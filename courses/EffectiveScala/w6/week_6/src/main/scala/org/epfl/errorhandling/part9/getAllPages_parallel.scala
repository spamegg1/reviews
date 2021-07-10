package org.epfl.errorhandling.part9

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object parallel:

  def getPagesCount(): Future[Int] = Future(42)

  def getPage(page: Int): Future[String] = Future(s"Page $page")

  def getAllPages(): Future[Seq[String]] =
    getPagesCount().flatMap { totalPages =>
      Future.traverse(1 to totalPages)(getPage)
    }

end parallel
