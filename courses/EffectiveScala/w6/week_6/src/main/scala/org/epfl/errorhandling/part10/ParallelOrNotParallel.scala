package org.epfl.errorhandling.part10

import scala.concurrent.{ Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration.Inf

/**
 *  This code compares different ways to kick off two calculations
 *  in parallel. It also shows a counter example where a for
 *  comprehension is used in a naive way, which result in the
 *  calculations running sequentially
 */
def costlyCalculation1(n: Long): Future[Long] =
  Future {
    concurrent.blocking(Thread.sleep(n * 1000))
    n * 4
  }

def costlyCalculation2(n: Long): Future[Long] =
  Future {
    concurrent.blocking(Thread.sleep(n * 1000))
    n + 3
  }

@main def runParallelOrNot(): Unit =
  println("kicking of calculation 1")

  val result1 =
    for
      calc1 <- costlyCalculation1(3)
      calc2 <- costlyCalculation2(3)
    yield (calc1, calc2)              // Calculations run in sequence
  
  result1.foreach(r => println(s"Result1 = $r"))

  println("kicking of calculation 2")

  val calc1F = costlyCalculation1(3)  // Start calculations in parallel...
  val calc2F = costlyCalculation2(3)
  
  val result2 =
    for
      calc1 <- calc1F
      calc2 <- calc2F
    yield (calc1, calc2)
  
  result2.foreach(r => println(s"Result2 = $r"))
  
  println("kicking of calculation 3")
  
  val result3 =
    for
      _ <- Future.unit                // Set container type to Future
      calc1F = costlyCalculation1(3)  // Start calculations in parallel...
      calc2F = costlyCalculation2(3)
      calc1 <- calc1F
      calc2 <- calc2F
    yield (calc1, calc2)
  
  result3.foreach(r => println(s"Result3 = $r"))
  
  // Wait until all results are in and allow output to stdout to be printed (200ms delay)
  Await.result(Future.sequence(List(result1, result2, result3)), Inf)
  Thread.sleep(200)