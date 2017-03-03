// Copyright (c) 2015 Novus Partners
// https://www.novus.com
//
// Proprietary license. Return only to Novus Partners. Do not distribute.
import sbt._
import sbt.Keys._

object TacticalEngineerTestBuild extends Build {

  lazy val project = Project(
    id = "tactical-engineer-test",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "tactical-engineer-test",
      organization := "com.novus.recruiting",
      version := "1.0",
      scalaVersion := "2.10.5",
      libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.0" % "test"
    )
  )
}
