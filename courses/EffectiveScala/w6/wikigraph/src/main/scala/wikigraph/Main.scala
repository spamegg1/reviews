import scala.concurrent.{ Await, ExecutionContext }
import scala.concurrent.duration.DurationInt
import wikigraph.implementations.Sqlite
import wikigraph.Articles.ArticleId
import wikigraph.Wikigraph
import ExecutionContext.Implicits.global

/**
  * Use this function to test your implementations and to
  * explore the dataset.
  * 
  * Run with `sbt run`
  */
@main def run() =
  val wiki = Sqlite()
  val scala = ArticleId(3254510)
  val python = ArticleId(23862)
  val assembly = ArticleId(1368)
  val jvm = ArticleId(16389)
  val analyser = Wikigraph(wiki)

  println(Await.result(wiki.nameOfArticle(scala).value, 2.minutes))
  println(Await.result(wiki.linksFrom(python).value, 10.minutes))
  println(Await.result(analyser.breadthFirstSearch(scala, assembly, 20).value,
                       20.minutes))
  println(Await.result(analyser.breadthFirstSearch(scala, jvm, 20).value,
                       20.minutes))
  println(Await.result(analyser.breadthFirstSearch(scala, python, 20).value,
                       20.minutes))
  println(Await.result(analyser.breadthFirstSearch(python, scala, 20).value,
                       20.minutes))
  println(Await.result(analyser.distanceMatrix(
    List("Scala (programming language)",
         "Python (programming language)",
         "Assembly language",
         "Java virtual machine",
         "Programming language")).value, 20.minutes))
  println(Await.result(analyser.distanceMatrix(
    List("École Polytechnique Fédérale de Lausanne",
         "Scala (programming language)"), 20).value, 20.minutes))
  println(Await.result(analyser.breadthFirstSearch(python, scala, 5).value,
                       20.minutes))
  println(Await.result(wiki.searchId("Scala").value, 2.days))
