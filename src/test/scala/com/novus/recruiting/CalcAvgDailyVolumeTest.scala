// Copyright (c) 2015 - 2017 Novus Partners
// https://www.novus.com
package com.novus.recruiting

import scala.collection.immutable.SortedMap

import org.scalatest._
import org.scalatest.OptionValues._
import TacticalEngineeringUtils._

class CalcAvgDailyVolumeTest extends FlatSpec with Matchers {

  private val everydayIsBusinessDay = (n: Int) => true

  private def shouldEqualWithinFourDecimals(option: Option[Double], expected: Double) =
    withClue(s"The average should have been about $expected, but: ") { option.value should be(expected +- 0.0001) }

  private def shouldNotResolveAnAverage(option: Option[Double]) =
    withClue("There is no possible average, but: ") { option should be(None) }

  """Our function for calculating Average Daily Volume with only business days and full range""" should
  """average 3 days of volume data""" in {
    val data = SortedMap(
      1 -> Some(2.0),
      2 -> Some(4.0),
      3 -> Some(3.0))
    val avg = calcAvgDailyVolume(data, everydayIsBusinessDay, 3)
    shouldEqualWithinFourDecimals(avg, 3.0)
  }

  it should """average 4 days of volume data where some days had no data available""" in {
    val data = SortedMap(
      1 -> Some(3.0),
      2 -> None,
      3 -> Some(1.0),
      4 -> Some(2.0),
      5 -> Some(2.0))
    val avg = calcAvgDailyVolume(data, everydayIsBusinessDay, 4)
    shouldEqualWithinFourDecimals(avg, 2.0)
  }

  it should """average 5 days of volume data where some days had 0.0 volume days""" in {
    val data = SortedMap(
      1 -> Some(0.0),
      2 -> Some(0.0),
      3 -> Some(1.0),
      4 -> Some(2.0),
      5 -> Some(2.0))
    val avg = calcAvgDailyVolume(data, everydayIsBusinessDay, 5)
    shouldEqualWithinFourDecimals(avg, 1.0)
  }

  it should """average 4 days of volume data that includes both 0.0 volume days and no-data-available days""" in {
    val data = SortedMap(
      1 -> Some(9.0),
      2 -> Some(0.0),
      3 -> None,
      4 -> Some(10.0),
      5 -> Some(5.0))
    val avg = calcAvgDailyVolume(data, everydayIsBusinessDay, 4)
    shouldEqualWithinFourDecimals(avg, 6.0)
  }

  """Our function for calculating Average Daily Volume with only business days and variable ranges""" should
  """average 3 day range from a dataset of 5 days""" in {
    val data = SortedMap(
      1 -> Some(3.0),
      2 -> Some(5.0),
      3 -> Some(1.0),
      4 -> Some(4.0),
      5 -> Some(1.0))
    val avg = calcAvgDailyVolume(data, everydayIsBusinessDay, 3)
    shouldEqualWithinFourDecimals(avg, 3.0)
  }

  it should """average 4 day range from a dataset of 6 days having some no-data-available days""" in {
    val data = SortedMap(
      1 -> Some(2.0),
      2 -> None,
      3 -> Some(1.0),
      4 -> Some(4.0),
      5 -> Some(1.0),
      6 -> Some(3.0))
    val avg = calcAvgDailyVolume(data, everydayIsBusinessDay, 4)
    shouldEqualWithinFourDecimals(avg, 2.0)
  }

  it should """average 20 day range from a dataset of only 3 days""" in {
    val data = SortedMap(
      1 -> Some(5.0),
      2 -> Some(4.0),
      3 -> Some(3.0))
    val avg = calcAvgDailyVolume(data, everydayIsBusinessDay, 20)
    shouldEqualWithinFourDecimals(avg, 4.0)
  }

  it should """average 10 day range from a dataset of 5 days having some no-data-available days""" in {
    val data = SortedMap(
      1 -> Some(4.0),
      2 -> None,
      3 -> Some(3.0),
      4 -> None,
      5 -> Some(2.0))
    val avg = calcAvgDailyVolume(data, everydayIsBusinessDay, 10)
    shouldEqualWithinFourDecimals(avg, 3.0)
  }

  private val NonBusinessDays = Set(2,5,7)
  private val businessDayLookup = (d: Int) => !NonBusinessDays.contains(d)

  """Our function for calculating Average Daily Volume with some non-business days and variable ranges""" should
  """average 3 day range excluding non-business days from a dataset of 6 days""" in {
    val data = SortedMap(
      1 -> Some(2.0),
      2 -> Some(3.0),
      3 -> Some(4.0),
      4 -> Some(0.0),
      5 -> Some(2.0),
      6 -> Some(1.0))
    val avg = calcAvgDailyVolume(data, businessDayLookup, 3)
    shouldEqualWithinFourDecimals(avg, 2.0)
  }
  it should """average 3 day range excluding non-business days from a dataset of 6 days that has a no-data-day""" in {
    val data = SortedMap(
      1 -> Some(2.0),
      2 -> Some(3.0),
      3 -> None,
      4 -> Some(4.0),
      5 -> Some(2.0),
      6 -> Some(3.0))
    val avg = calcAvgDailyVolume(data, businessDayLookup, 3)
    shouldEqualWithinFourDecimals(avg, 3.0)
  }

  it should """average 10 day range excluding non-business days from a dataset of 8 days that has no-data-days""" in {
    val data = SortedMap(
      1 -> Some(3.0),
      2 -> Some(1.0),
      3 -> Some(5.0),
      4 -> None,
      5 -> Some(8.0),
      6 -> Some(5.0),
      7 -> Some(10.0),
      8 -> Some(3.0))
    val avg = calcAvgDailyVolume(data, businessDayLookup, 10)
    shouldEqualWithinFourDecimals(avg, 4.0)
  }

  """Finally, our function for calculating Average Daily Volume must handle edge cases, and therefore""" should
  """resolve no average for empty datasets""" in {
    val avg = calcAvgDailyVolume(SortedMap.empty[Int, Option[Double]], businessDayLookup, 100)
    shouldNotResolveAnAverage(avg)
  }

  it should """resolve no average for datasets where every day is no-data-available""" in {
    val data = SortedMap(1 -> None, 2 -> None, 3 -> None, 4 -> None, 5 -> None)
    val avg = calcAvgDailyVolume(data, businessDayLookup, 5)
    shouldNotResolveAnAverage(avg)
  }

  it should """find an average of 0.0 for datasets where every day is 0.0""" in {
    val data = SortedMap(1 -> Some(0.0), 2 -> Some(0.0), 3 -> Some(0.0), 4 -> Some(0.0), 5 -> Some(0.0))
    val avg = calcAvgDailyVolume(data, businessDayLookup, 5)
    shouldEqualWithinFourDecimals(avg, 0.0)
  }

  it should """resolve no average when the business day calendar does not think any day is a business day""" in {
    val data = SortedMap(1 -> Some(0.0), 2 -> Some(0.0), 3 -> Some(0.0), 4 -> Some(0.0), 5 -> Some(0.0))
    val nope = (n: Int) => false
    val avg = calcAvgDailyVolume(data, nope, 5)
    shouldNotResolveAnAverage(avg)
  }

  it should """resolve no average when the range is 0""" in {
    val data = SortedMap(1 -> Some(1.0), 2 -> Some(4.0), 3 -> Some(8.0))
    val avg = calcAvgDailyVolume(data, everydayIsBusinessDay, 0)
    shouldNotResolveAnAverage(avg)
  }

  it should """resolve no average when the range is negative""" in {
    val data = SortedMap(1 -> Some(1.0), 2 -> Some(4.0), 3 -> Some(8.0))
    val avg = calcAvgDailyVolume(data, everydayIsBusinessDay, -100)
    shouldNotResolveAnAverage(avg)
  }

}
