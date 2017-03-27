# Tactical Engineering Scala Test

## Summary

This test simulates some realistic (if simple) Scala coding tasks you will regularly encounter as a Tactical Software Engineer at Novus. Instead of writing yet another recursive factorial function, you will demonstrate ability to solve practical, real-world problems using the Scala language and SDK.

If you are new to Scala, that's fine - Tactical Engineers are expected to rapidly acquire skills on-the-job. Your success in this test will demonstrate that quality. See *Resources* further down for some helpful links to help you prepare.

Good luck!

## Prerequisites

You must have the following installed on your local development environment:

- Java 1.7.0
- SBT (Simple Build Tool) v0.13.8
  See: http://www.scala-sbt.org/0.13/tutorial/Setup.html

## QUICKSTART

At the command line, execute

```
sbt
```

This will start the SBT console.  It may take a minute or two to download libraries needed for compilation.  When you see the `>` prompt, enter


```
  test
```

to execute the unit test suite. Note that many of the the tests are currently failing.

While you are focusing on fixing a single test suite, you can use


```
  test-only <test class name>
```

to execute a single test suite at a time.

If you are down to just one or two failing tests, you can use

```
  test-quick
```

to execute just the previously failing tests.

You can also use

```
  clean
```

to reset your build if you want to re-compile from scratch.

For an interactive console where you can try out your Scala code, enter

```
  console
```

When you are done, exit the console by typing

```
  :quit
```

## Instructions

Your goal is to make the unit tests in this project pass. To this end, you are only allowed to edit the source file:

`src/main/scala/com/novus/recruiting/TacticalEngineeringUtils.scala`

You are encouraged to read (but not change!) the unit tests located under the directory

`src/test/scala/com/novus/recruiting/`

Rules:
- You *may not* modify the unit tests to get them to pass. You may add more test cases, however!
- You *may not* modify the file `project/TacticalEngineerTestBuild.scala` to add libraries beyond the standard Scala SDK or to change the Scala version.
- Scala runs on the JVM. You *may* use Java interop.
- If you feel it necessary - for example, to better organize your source code - you *may* add source files under src/. This is not required however. If you prefer to keep all your source code in TacticalEngineeringUtils.scala, that is acceptable as well.

## Criteria
Novus will analyze your code for the following qualities:

- Correctness: the unit tests must pass.
- Style: idiomatic, functional Scala style and language usage.
- Craftsmanship: attention to naming, comments, formatting, structure.

Again, you are welcome and encouraged to use the standard Scala library (do not re-invent `StringOps.mkString`, for example).

# Resources

If you are new to the Scala language and SDK, the following resources may prove useful:

- [Twitter Scala School](https://twitter.github.io/scala_school/)
- [Learn X in Y Minutes - Scala](http://learnxinyminutes.com/docs/scala/)
- [scala.Option Cheat Sheet](http://tonymorris.github.io/blog/posts/scalaoption-cheat-sheet/)
- [Idiomatic Scala: Your Options Do Not Match](http://blog.originate.com/blog/2014/06/15/idiomatic-scala-your-options-do-not-match/)

The unit tests are written in ScalaTest. You can learn more about the syntax of the tests here:

- [ScalaTest: Using Matchers](http://www.scalatest.org/user_guide/using_matchers)

# Legal

Copyright (c) 2015-2017 Novus Partners

https://www.novus.com
