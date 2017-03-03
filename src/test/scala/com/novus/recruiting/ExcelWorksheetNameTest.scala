// Copyright (c) 2015 - 2017 Novus Partners
// https://www.novus.com
package com.novus.recruiting

import org.scalatest._
import TacticalEngineeringUtils._

class ExcelWorksheetTest extends FlatSpec with Matchers {

  private def resultShouldBeInTheSameOrder(result: Seq[(String, String)], original: Seq[String]) =
    result.map(_._1) should contain theSameElementsInOrderAs original

  private def resultShouldHaveUniqueNames(result: Seq[(String, String)], original: Seq[String]) =
    withClue("Number of worksheets: ") { result.map(_._2).distinct.size should be(original.size) }

  private def disallowNameHistoryAnyCase(names: Seq[String]) = {
    import org.scalatest.Inspectors._
    import org.scalautils.StringNormalizations._
    forAll (names) { name =>
      withClue(""""History" is not a permitted worksheet name (case-insensitive)""") {
        (name should not equal ("history")) (after being lowerCased)
      }
    }
  }

  private def baselineRequirements(names: Seq[String]): Seq[(String, String)] = {
    val result = sanitizeWorksheetNames(names)
    resultShouldBeInTheSameOrder(result, names)
    resultShouldHaveUniqueNames(result, names)
    result
  }

  "Our utility for cleaning Microsoft Excel worksheet names" should
  "ensure the names are made unique" in {
    val names = "sheet1" :: "sheet1" :: Nil
    baselineRequirements(names)
  }

  it should """ensure names are unique after being trimmed to 31 characters""" in {
    val names = "AReallyLongNameThatWillBeTrimmedByExcel" ::
      "AReallyLongNameThatWillBeTrimmedByExcelAlso" :: Nil

    val result = baselineRequirements(names)

    // Simulate Excel trimming sheet names when you actually create the workbook
    val newNamesTrimmed = result.map(n => n._1 -> n._2.take(31))
    resultShouldHaveUniqueNames(newNamesTrimmed, names)
  }

  it should """remove characters not allowed in worksheet names""" in {
    val names = """sheet\""" ::
    """sheet/""" ::
    """sheet*""" ::
    """sheet?""" ::
    """sheet[""" ::
    """sheet]""" :: Nil
    val result = baselineRequirements(names)
    val newNames = result.map(_._2)

    every(newNames) should contain noneOf ('\\','/','*','?','[',']')
  }

  it should """accept sheets consisting of all whitespace""" in {
    val names = " " :: "  " :: "   " :: Nil
    baselineRequirements(names)
  }

  it should """not have any sheets named "History"""" in {
    val names = "History" :: "sheet1" :: Nil
    val result = baselineRequirements(names)
    val newNames = result.map(_._2)
    disallowNameHistoryAnyCase(newNames)
  }

  it should """not have any sheets named "history"""" in {
    val names = "history" :: "sheet1" :: Nil
    val result = baselineRequirements(names)
    val newNames = result.map(_._2)
    disallowNameHistoryAnyCase(newNames)
  }

  it should """rename worksheets that are empty strings""" in {
    val names = "" :: "" :: Nil
    val result = baselineRequirements(names)
    val newNames = result.map(_._2)
    every (newNames) should not be ('empty)
  }

  it should """pass through empty Seqs""" in {
    val names: Seq[String] = Nil
    val result = baselineRequirements(names)
    result should be ('empty)
  }
}
