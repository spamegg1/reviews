package org.epfl.errorhandling.part9

import scala.concurrent.{ Await, Future, Promise, ExecutionContext}
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration.Inf

private def futureSequenceFailFast[A](futures: Seq[Future[A]]): Future[Seq[A]] =
  val fastFailResult: Promise[Seq[A]] = Promise[Seq[A]]()

  futures.foreach { f =>
    f.onComplete {
      case Failure(ex) => fastFailResult.tryFailure(ex)
      case _ =>
    }
  }

  fastFailResult.completeWith(Future.sequence(futures))
  fastFailResult.future
end futureSequenceFailFast

extension [A](future: Future.type)
  def sequenceFailFast(futures: Seq[Future[A]]) = futureSequenceFailFast(futures)

def potentiallyFailingFuture(n: Int, failForValue: Int)
     (using ExecutionContext): Future[Int] = 
  if n == failForValue then
    Future {
      concurrent.blocking(Thread.sleep(500))
      n * 100
      throw new Exception(s"Bad stuff happens when n == $n")
    }
  else
    Future {
      concurrent.blocking(Thread.sleep(4000))
      n * 10
    }
end potentiallyFailingFuture

/**
 *  `Future.sequence` doesn't implement fast-failure: this means that
 *  a completion with a Failure of one of the Futures in the sequence
 *  on which it operates, [in general] doesn't result in the returned
 *  Future to immediately fail.
 */
@main def runFailFast(): Unit =

  val xs = List(1, 2, 6, 5, 20, 3, 45)

  // Set variable `failForValue` to the value of an element in the "xs" collection - or not
  val failForValue = 45

  println(s"Starting some computation...")
  val listOfFutures =
    xs.map(number => potentiallyFailingFuture(number, failForValue))

  val seqFutures = Future.sequenceFailFast(listOfFutures)  // This version fails fast...
  // val seqFutures = Future.sequence(listOfFutures)       // The library version doesn't

  seqFutures.onComplete {
    case Success(result) => println(s"Result = $result")
    case Failure(ex) => println(s"Failed: $ex")
  }

  // Wait until all results are in and allow output to stdout to be printed (200ms delay)
  Await.ready(seqFutures, Inf)
  Thread.sleep(200)
