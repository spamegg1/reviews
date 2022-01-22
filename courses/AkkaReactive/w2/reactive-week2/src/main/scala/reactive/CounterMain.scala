package reactive

import akka.actor.Actor
import akka.actor.Props

class CounterMain extends Actor {
  val counter = context.actorOf(Props[Counter], "counter")

  counter ! "incr"
  counter ! "incr"
  counter ! "get"

  def receive = {
    case count: Int ⇒
      println(s"count was $count")
      context.stop(self)
  }
}