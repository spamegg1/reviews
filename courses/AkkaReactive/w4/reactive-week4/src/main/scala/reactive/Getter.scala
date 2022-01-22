package reactive

import akka.actor.Actor
import akka.pattern.pipe
import java.util.concurrent.Executor
import akka.actor.ActorLogging
import akka.actor.Status
import scala.concurrent.ExecutionContext
import org.jsoup.Jsoup
import scala.collection.JavaConverters._

class Getter(url: String, depth: Int) extends Actor {

  implicit val executor = context.dispatcher.asInstanceOf[Executor with ExecutionContext]
  def client: WebClient = AsyncWebClient

  client get url pipeTo self

  def receive = {
    case body: String =>
      for (link <- findLinks(body))
        context.parent ! Controller.Check(link, depth)
      context.stop(self)
    case _: Status.Failure => context.stop(self)
  }

  def findLinks(body: String): Iterator[String] = {
    val document = Jsoup.parse(body, url)
    val links = document.select("a[href]")
    for {
      link <- links.iterator().asScala
    } yield link.absUrl("href")
  }
}