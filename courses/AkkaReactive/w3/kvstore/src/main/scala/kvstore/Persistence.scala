package kvstore

import akka.actor.{Props, Actor, actorRef2Scala}
import scala.util.Random

object Persistence:
  case class Persist(key: String, valueOption: Option[String], id: Long)
  case class Persisted(key: String, id: Long)

  class PersistenceException extends Exception("Persistence failure")

  def props(flaky: Boolean): Props = Props(classOf[Persistence], flaky)

class Persistence(flaky: Boolean) extends Actor:
  import Persistence.*

  def receive =
    case Persist(key, _, id) =>
      if !flaky || Random.nextBoolean() then sender() ! Persisted(key, id)
      else throw PersistenceException()

