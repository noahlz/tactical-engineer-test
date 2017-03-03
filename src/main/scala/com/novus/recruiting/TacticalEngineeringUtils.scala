// Copyright (c) 2015 - 2017 Novus Partners
// https://www.novus.com
package com.novus.recruiting

import scala.collection.immutable.SortedMap

object TacticalEngineeringUtils {

  /**
   * Sanitizes Microsoft Excel worksheet names.
   *
   * Microsoft Excel worksheet names are subject to the following rules:
   * - A sheet name must be 1 - 31 characters. Excel only takes the first 31 characters
   *   for the sheet name, truncating the remaining characters from the sheet name.
   * - A sheet name cannot be blank (empty string)
   * - A sheet name cannot include any of the following characters: / \ * ? ] [
   * - A sheet name *can* be all whitespace characters (example: "   ").
   * - A sheet name cannot be the word "History" (case-insensitive)
   * - All sheet names in a given sequence must be unique.
   *
   * @param worksheets The Seq of worksheet names.
   * @return A Seq of tuples, where _1 is the original name and _2 is the corresponding
   *         santized name, returned in the same order as the input [[worksheets]].
   */
  def sanitizeWorksheetNames(worksheets: Seq[String]): Seq[(String, String)] = {
    // FIXME: replace this with an implementation.
    worksheets.map(s => s -> s)
  }

  /**
   * Calculate Average Daily Volume for a security.
   *
   * Given a sorted map of calendar days to corresponding trading volume, and a function that looks up
   * if a given day is a business day or not, calculate the average volume over a range of days from the
   * beginning of the dataset. Days with "no-data-available" (represented as [[None]]) do not count toward
   * the average and are skipped.
   *
   * So a dataset of
   *
   * 1 -> Some(100.0)
   * 2 -> Some(300.0)
   * 3 -> None
   * 4 -> Some(200.0)
   * 5 -> Some(300.0)
   *
   * where [[isBusinessDay]] returns [[false]] for each day, would return a result of [[Some(200.0)]]
   * for a [[range]] of 3 ([[(100 + 300 + 200) / 3)]]).
   *
   * If [[isBusinessDay(4)]] returned [[false]], the average daily volume would be
   * [[Some(233.3333)]] for a range of 3 ([[(100 + 300 + 300) / 3)]]).
   *
   * @param data map of daily volume for a given security, where the key is the day and value is the trading volume on that day.
   * @param isBusinessDay a supplied lookup function that indicates if a given day was a business day, and should be excluded from the average calculation.
   * @param range the number of business days to take from the data set for the calculation (excluding no-data days and non-business days).
   * @return Some value representing the average daily volume (could be 0.00), or None if no volume was found given the parameters.
   */
  def calcAvgDailyVolume(data: SortedMap[Int, Option[Double]], isBusinessDay: Int => Boolean, range: Int): Option[Double] = {
    // FIXME: replace this with an implementation.
    Some(0.0)
  }

  /**
   * Given a sequence of integers, finds the sub-sequences of contiguous integers. Integers x and y
   * in a sequence are contiguous if y = x + 1.
   *
   * For example  [[findContiguous(List(1,2,3,5,6,7))]] should return [[List(1,2,3) :: List(5,6,7) :: Nil]]
   *
   * The sub-sequences might not be in the order that that they appeared in the input data, but the contents
   * of each sub-sequence should be in numerical order.
   *
   * @param data The input sequence of integers.
   * @return A list of lists representing the sub-sequences from the input. original input data.
   */
  def findContiguous(data: Seq[Int]): List[List[Int]] = {
    // FIXME: replace this with an implementation.
    List(data.toList)
  }

}
