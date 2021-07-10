package org.epfl.errorhandling.part1

def attemptSomething(): Unit =
  println("So far, so good")
  println("Still there")
  throw RuntimeException("We can’t continue")
  println("You will never see this")

@main def run(): Unit =
//  try
    attemptSomething()
//  catch
//    case exn: RuntimeException =>
//      System.err.println(s"Something went wrong: $exn")
//      println("Stopping the program")
