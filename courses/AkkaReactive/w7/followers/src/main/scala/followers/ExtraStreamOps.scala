package followers
import akka.event.Logging
import akka.stream.{Attributes, FlowShape, Inlet, Outlet}
import akka.stream.scaladsl.Flow
import akka.stream.stage.*
import akka.util.ByteString

trait ExtraStreamOps:

  extension [T, M] (f: Flow[T, T, M]) def logAllEvents(name: String) =
    // proposed as idea to akka streams: https://github.com/akka/akka/issues/24859
    val l = new GraphStage[FlowShape[T, T]]:
      val in = Inlet[T]("logAllEvents.in")
      val out = Outlet[T]("logAllEvents.out")

      override def shape: FlowShape[T, T] = FlowShape.of(in, out)

      override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = new GraphStageLogic(shape)
        with StageLogging
        with InHandler with OutHandler {

        val paddedName = name.take(8).padTo(8, ' ').replace('\n', ' ')
        val level = Logging.InfoLevel

        override def onPush(): Unit =
          val el = grab(in)
          val els = el match
            case bs: ByteString => bs.utf8String
            case other => other
          log.log(level, s"push [${els.toString().take(18).padTo(18, ' ').replace('\n', ' ')}] >>> {$paddedName}")
          push(out, el)

        override def onPull(): Unit =
          log.log(level, s"pull                      <~~ {$paddedName}")
          pull(in)

        override def onUpstreamFinish(): Unit =
          log.log(level, s"complete                  ==> {$paddedName}")
          super.onUpstreamFinish()
        override def onUpstreamFailure(ex: Throwable): Unit =
          log.log(level, s"fail(onError)             xx> {$paddedName} ${ex.getMessage}")
          super.onUpstreamFailure(ex)
        override def onDownstreamFinish(cause: Throwable): Unit =
          log.log(level, s"cancel                    x~~ {$paddedName}")
          super.onDownstreamFinish(cause)

        setHandlers(in, out, this)
      }

    val r = new GraphStage[FlowShape[T, T]]:
      val in = Inlet[T]("logAllEvents.in")
      val out = Outlet[T]("logAllEvents.out")

      override def shape: FlowShape[T, T] = FlowShape.of(in, out)

      override def createLogic(inheritedAttributes: Attributes): GraphStageLogic = new GraphStageLogic(shape)
        with StageLogging
        with InHandler with OutHandler {

        val paddedName = name.take(8).padTo(8, ' ').replace('\n', ' ')
        val level = Logging.InfoLevel

        override def onPush(): Unit =
          val el = grab(in)
          val els = el match
            case bs: ByteString => bs.utf8String
            case other => other
          log.log(level, s"push                          {$paddedName} >>> [${els.toString.take(18).padTo(18, ' ').replace('\n', ' ')}]")
          push(out, el)

        override def onPull(): Unit =
          log.log(level, s"pull from downstr             {$paddedName} <~~")
          pull(in)

        override def onUpstreamFinish(): Unit =
          log.log(level, s"complete                      {$paddedName} ==>")
          super.onUpstreamFinish()
        override def onUpstreamFailure(ex: Throwable): Unit =
          log.log(level, s"fail(onError)                 {$paddedName} xx> ${ex.getMessage}")

          super.onUpstreamFailure(ex)
        override def onDownstreamFinish(cause: Throwable): Unit =
          log.log(level, s"cancel from downstr           {$paddedName} x~~")
          super.onDownstreamFinish(cause)

        setHandlers(in, out, this)
      }

    Flow.fromGraph(l).via(f).via(r)

