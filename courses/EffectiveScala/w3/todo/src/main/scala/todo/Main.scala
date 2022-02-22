package todo

import cats.effect.*
import cats.implicits.*
import fs2.Stream
import org.http4s.*
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.{Router, Server}
import org.http4s.syntax.kleisli.*
import org.http4s.server.middleware.CORS

/**
 * This object setups and runs the webserver.
 *
 * You should change InMemoryModel in this file to PersistentModel 
 * when you have successfully implemented PersistentModel. 
 * Otherwise you should NOT modify this file.
 */
object Main extends IOApp:
  private def app(blocker: Blocker): HttpApp[IO] =
    Router
      .define("/api" -> CORS(TodoService(InMemoryModel).service))
             (AssetService.service(blocker))
      .orNotFound

  private def server(blocker: Blocker): Resource[IO, Server] =
    EmberServerBuilder
      .default[IO]
      .withHost("0.0.0.0")
      .withPort(3000)
      .withHttpApp(app(blocker))
      .build

  private val program: Stream[IO, Unit] =
    for
      blocker <- Stream.resource(Blocker[IO])
      server <- Stream.resource(server(blocker))
      _ <- Stream.eval(IO(println(s"Started server on: ${server.address}")))
      _ <- Stream.never[IO].covaryOutput[Unit]
    yield ()

  override def run(args: List[String]): IO[ExitCode] =
    program
      .compile
      .drain
      .as(ExitCode.Success)
