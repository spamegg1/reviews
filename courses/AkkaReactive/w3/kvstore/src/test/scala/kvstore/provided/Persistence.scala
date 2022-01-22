/**
 * Copyright (C) 2013-2015 Typesafe Inc. <http://www.typesafe.com>
 */
package kvstore.provided

import akka.actor.{ Props, Actor, actorRef2Scala }
import scala.util.Random

object Persistence:
  def props(flaky: Boolean): Props = Props(classOf[Persistence], flaky)

class Persistence(flaky: Boolean) extends Actor:
  import kvstore.Persistence.*

  private def newFailCount: Int = if flaky then Random.nextInt(4) else 0
  var failSteps: Int = newFailCount

  def receive =
    case Persist(key, value, id) =>
      if failSteps == 0 then
        sender() ! Persisted(key, id)
        failSteps = newFailCount
      else failSteps -= 1

