package reactive

import akka.actor.Actor
import akka.actor.Props
import scala.concurrent.duration._
import akka.actor.ReceiveTimeout
import akka.cluster.Cluster
import akka.cluster.ClusterEvent
import akka.actor.RootActorPath
import akka.actor.Identify
import akka.actor.ActorIdentity
import akka.actor.ActorLogging
import akka.actor.Terminated

class Main extends Actor {

  import Receptionist._

  val receptionist = context.actorOf(Props[Receptionist], "receptionist")
  context.watch(receptionist) // sign death pact
  
  receptionist ! Get("http://www.google.com")
  receptionist ! Get("http://www.google.com/1")
  receptionist ! Get("http://www.google.com/2")
  receptionist ! Get("http://www.google.com/3")
  receptionist ! Get("http://www.google.com/4")
  receptionist ! Get("http://www.google.com")

  context.setReceiveTimeout(10.seconds)

  def receive = {
    case Result(url, set) =>
      println(set.toVector.sorted.mkString(s"Results for '$url':\n", "\n", "\n"))
    case Failed(url, reason) =>
      println(s"Failed to fetch '$url': $reason\n")
    case ReceiveTimeout =>
      context.stop(self)
  }

  override def postStop(): Unit = {
    AsyncWebClient.shutdown()
  }

}

class ClusterMain extends Actor {

  import Receptionist._

  val cluster = Cluster(context.system)
  cluster.subscribe(self, classOf[ClusterEvent.MemberUp])
  cluster.subscribe(self, classOf[ClusterEvent.MemberRemoved])
  cluster.join(cluster.selfAddress)

  val receptionist = context.actorOf(Props[ClusterReceptionist], "receptionist")
  context.watch(receptionist) // sign death pact

  def getLater(d: FiniteDuration, url: String): Unit = {
    import context.dispatcher
    context.system.scheduler.scheduleOnce(d, receptionist, Get(url))
  }

  getLater(Duration.Zero, "http://www.google.com")

  def receive = {
    case ClusterEvent.MemberUp(member) =>
      if (member.address != cluster.selfAddress) {
        getLater(1.seconds, "http://www.google.com")
        getLater(2.seconds, "http://www.google.com/0")
        getLater(2.seconds, "http://www.google.com/1")
        getLater(3.seconds, "http://www.google.com/2")
        getLater(4.seconds, "http://www.google.com/3")
        context.setReceiveTimeout(3.seconds)
      }
    case Result(url, set) =>
      println(set.toVector.sorted.mkString(s"Results for '$url':\n", "\n", "\n"))
    case Failed(url, reason) =>
      println(s"Failed to fetch '$url': $reason\n")
    case ReceiveTimeout =>
      cluster.leave(cluster.selfAddress)
    case ClusterEvent.MemberRemoved(m, _) =>
      context.stop(self)
  }

}

class ClusterWorker extends Actor with ActorLogging {
  val cluster = Cluster(context.system)
  cluster.subscribe(self, classOf[ClusterEvent.MemberUp])
  //cluster.subscribe(self, classOf[ClusterEvent.MemberRemoved])
  val main = cluster.selfAddress.copy(port = Some(2552))
  cluster.join(main)

  def receive = {
    case ClusterEvent.MemberUp(member) =>
      if (member.address == main)
        context.actorSelection(RootActorPath(main) / "user" / "app" / "receptionist") ! Identify("42")
    case ActorIdentity("42", None) => context.stop(self)
    case ActorIdentity("42", Some(ref)) =>
      log.info("receptionist is at {}", ref)
      context.watch(ref)
    case Terminated(_) => context.stop(self)
    case ClusterEvent.MemberRemoved(m, _) =>
      if (m.address == main) context.stop(self)
  }

  override def postStop(): Unit = {
    AsyncWebClient.shutdown()
  }

}