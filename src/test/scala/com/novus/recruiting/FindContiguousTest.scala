// Copyright (c) 2015 - 2017 Novus Partners
// https://www.novus.com
package com.novus.recruiting

import org.scalatest._
import TacticalEngineeringUtils._

class FindContiguousTest extends FlatSpec with Matchers {

  def expect(result: List[List[Int]], expected: List[List[Int]]) = {
    result should contain theSameElementsAs expected
    every (result) shouldBe sorted
    result
  }


  """Our utility for locating contiguous sequences of numbers within a list""" should
  """handle a single contiguous sequence""" in {
    val result = findContiguous(Seq(1,2,3,4,5,6))
    expect(result, List(1,2,3,4,5,6) :: Nil)
  }

  it should """handle an empty sequence""" in {
    val result = findContiguous(Nil)
    expect(result, Nil)
  }

  it should """identify a squence with one gap""" in {
    val result = findContiguous(Seq(1,2,3,5,6,7))
    expect(result, List(1,2,3) :: List(5,6,7) :: Nil)
  }

  it should """identify a sequence with a gap of two numbers""" in {
    val result = findContiguous(Seq(1,2,3,6,7,8))
    expect(result, List(1,2,3) :: List(6,7,8) :: Nil)
  }

  it should """identify a gap in a sequence having negative numbers""" in {
    val result = findContiguous(Seq(-3,-2,-1,5,6,7))
    expect(result, List(-3,-2,-1) :: List(5,6,7) :: Nil)
  }

  it should """identify multiple gaps in a sequence""" in {
    val data = Seq(1,2,4,5,7,8)
    val result = findContiguous(data)
    expect(result, List(1,2) :: List(4,5) :: List(7,8) :: Nil)
  }

  it should """find where every list has a single element""" in  {
    val data = Seq(1,3,5,7)
    val result = findContiguous(data)
    result should contain theSameElementsAs(List(1) :: List(3) :: List(5) :: List(7) :: Nil)
  }

  it should """handle unsorted sequences of numbers as non-contiguous""" in {
    val data = Seq(1,2,3,1,2,3,2,3,4)
    val result = findContiguous(data)
    expect(result, List(1,2,3) :: List(1,2,3) :: List(2,3,4) :: Nil)
  }

  it should """handle a sequence in reverse-order as a non-contiguous list of single numbers""" in {
    val result = findContiguous(Seq(5,4,3,2,1))
    result should contain theSameElementsAs(List(5) :: List(4) :: List(3) :: List(2) :: List(1) :: Nil)
  }

}
