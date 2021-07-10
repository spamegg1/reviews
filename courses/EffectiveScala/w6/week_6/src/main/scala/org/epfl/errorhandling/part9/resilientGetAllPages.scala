package org.epfl.errorhandling.part9

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.*
import scala.util.Random
import scala.util.control.NonFatal

def getPagesCount(): Future[Int] = Future(42)

def getPage(page: Int): Future[String] =
  if Random.nextDouble() > 0.95 then
    Future.failed(Exception(s"Timeout when fetching page $page"))
  else Future(s"Page $page")

def resilientGetPage(page: Int): Future[String] =
  val maxAttempts = 3

  def attempt(remainingAttempts: Int): Future[String] =
    if remainingAttempts == 0 then
      Future.failed(Exception(s"Fetching page $page failed after $maxAttempts"))
    else
      println(s"Trying to fetch page $page ($remainingAttempts remaining attempts)")
      getPage(page)
        .recoverWith { case NonFatal(_) =>
          System.err.println(s"Fetching page $page failed...")
          attempt(remainingAttempts - 1)
        }

  attempt(maxAttempts)
end resilientGetPage

def resilientGetAllPages(): Future[Seq[String]] =
  getPagesCount().flatMap { totalPages =>
    Future.traverse(1 to totalPages)(resilientGetPage)
  }

@main def run() =
  resilientGetAllPages().onComplete(println)
