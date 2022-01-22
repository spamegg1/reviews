/**
  * Copyright (C) 2009-2015 Typesafe Inc. <http://www.typesafe.com>
  */
package actorbintree:

  import akka.actor.{ActorRef, ActorSystem, Props, actorRef2Scala}
  import akka.testkit.{ImplicitSender, TestKit, TestProbe}

  import scala.util.Random
  import scala.concurrent.duration.*

  class BinaryTreeSuite extends munit.FunSuite:
    // This Impl workaround is necessary because in Scala 3 a lazy val
    // cannot override a val. So we cannot override lazy val system
    // which breaks TestKitBase
    Impl()
    class Impl
        extends TestKit(ActorSystem("BinaryTreeSuite"))
        with ImplicitSender:

      import actorbintree.BinaryTreeSet.*

      def receiveN(
          requester: TestProbe,
          ops: Seq[Operation],
          expectedReplies: Seq[OperationReply]
      ): Unit =
        requester.within(5.seconds) {
          val repliesUnsorted = for i <- 1 to ops.size yield try
            requester.expectMsgType[OperationReply]
          catch
            case ex: Throwable if ops.size > 10 =>
              sys.error(s"failure to receive confirmation $i/${ops.size}\n$ex")
            case ex: Throwable =>
              sys.error(
                s"failure to receive confirmation $i/${ops.size}\nRequests:" + ops
                  .mkString("\n    ", "\n     ", "") + s"\n$ex"
              )
          val replies = repliesUnsorted.sortBy(_.id)
          if replies != expectedReplies then
            val pairs = (replies zip expectedReplies).zipWithIndex filter (x =>
              x._1._1 != x._1._2
            )
            fail(
              "unexpected replies:" + pairs
                .map(x =>
                  s"at index ${x._2}: got ${x._1._1}, expected ${x._1._2}"
                )
                .mkString("\n    ", "\n    ", "")
            )
        }

      def verify(probe: TestProbe, ops: Seq[Operation], expected: Seq[OperationReply]): Unit =
        val topNode = system.actorOf(Props[BinaryTreeSet]())

        ops foreach { op =>
          topNode ! op
        }

        receiveN(probe, ops, expected)
      // the grader also verifies that enough actors are created

      test("proper inserts and lookups (5pts)") {
        val topNode = system.actorOf(Props[BinaryTreeSet]())

        topNode ! Contains(testActor, id = 1, 1)
        expectMsg(ContainsResult(1, false))

        topNode ! Insert(testActor, id = 2, 1)
        topNode ! Contains(testActor, id = 3, 1)

        expectMsg(OperationFinished(2))
        expectMsg(ContainsResult(3, true))
        ()
      }

      test("instruction example (5pts)") {
        val requester = TestProbe()
        val requesterRef = requester.ref
        val ops = List(
          Insert(requesterRef, id = 100, 1),
          Contains(requesterRef, id = 50, 2),
          Remove(requesterRef, id = 10, 1),
          Insert(requesterRef, id = 20, 2),
          Contains(requesterRef, id = 80, 1),
          Contains(requesterRef, id = 70, 2)
        )

        val expectedReplies = List(
          OperationFinished(id = 10),
          OperationFinished(id = 20),
          ContainsResult(id = 50, false),
          ContainsResult(id = 70, true),
          ContainsResult(id = 80, false),
          OperationFinished(id = 100)
        )

        verify(requester, ops, expectedReplies)
      }


      test("behave identically to built-in set (includes GC) (40pts)") {
        val rnd = Random()
        def randomOperations(
            requester: ActorRef,
            count: Int
        ): Seq[Operation] =
          def randomElement: Int = rnd.nextInt(100)
          def randomOperation(requester: ActorRef, id: Int): Operation =
            rnd.nextInt(4) match
              case 0 => Insert(requester, id, randomElement)
              case 1 => Insert(requester, id, randomElement)
              case 2 => Contains(requester, id, randomElement)
              case 3 => Remove(requester, id, randomElement)

          for seq <- 0 until count yield randomOperation(requester, seq)

        def referenceReplies(
            operations: Seq[Operation]
        ): Seq[OperationReply] =
          var referenceSet = Set.empty[Int]
          def replyFor(op: Operation): OperationReply =
            op match
              case Insert(_, seq, elem) =>
                referenceSet = referenceSet + elem
                OperationFinished(seq)
              case Remove(_, seq, elem) =>
                referenceSet = referenceSet - elem
                OperationFinished(seq)
              case Contains(_, seq, elem) =>
                ContainsResult(seq, referenceSet(elem))

          for op <- operations yield replyFor(op)

        val requester = TestProbe()
        val topNode = system.actorOf(Props[BinaryTreeSet]())
        val count = 1000

        val ops = randomOperations(requester.ref, count)
        val expectedReplies = referenceReplies(ops)

        ops foreach { op =>
          topNode ! op
          if rnd.nextDouble() < 0.1 then topNode ! GC
        }
        receiveN(requester, ops, expectedReplies)
      }
