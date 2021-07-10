package org.epfl.errorhandling.part9

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object sequential:

  def getPagesCount(): Future[Int] = Future(42)

  def getPage(page: Int): Future[String] = Future(s"Page $page")

  def getAllPages(): Future[Seq[String]] =
    getPagesCount().flatMap { pagesCount =>
      (1 to pagesCount).foldLeft(Future.successful(Seq.empty[String])) {
        (eventualPreviousPages, pageNumber) =>
          eventualPreviousPages.flatMap { previousPages =>
            getPage(pageNumber)
              .map(pageContent => previousPages :+ pageContent)
          }
      }
    }

end sequential
