package todo

import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.circe.*
import org.http4s.circe.CirceEntityDecoder.*
import io.circe.Encoder
import io.circe.syntax.*
import todo.data.*

/**
 * This service provides the API for the user interface. The API endpoints
 * correspond to methods on the Model. Data is sent over HTTP as JSON.
 *
 * You should NOT modify this file.
 */
class TodoService(model: Model):
  import org.http4s.dsl.io.*
  import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
  import Codecs.given

  object Description extends QueryParamDecoderMatcher[String]("description")

  val service: HttpRoutes[IO] =
    HttpRoutes.of[IO]{
      case GET -> Root / "task" / IntVar(id) =>
        model.read(Id(id)) match
          case None => NotFound()
          case Some(task) => Ok(task.asJson)

      case req @ POST -> Root / "task" =>
        req.decode[Task]{ task =>
          val id = model.create(task)
          Ok(id)
        }

      case req @ POST -> Root / "task" / IntVar(id) =>
        req.decode[Task]{ task =>
          val updated = model.update(Id(id))(_ => task)
          Ok(updated)
        }

      case DELETE -> Root / "task" / IntVar(id) =>
        if model.delete(Id(id)) then
          Ok()
        else
          NotFound()

      case POST -> Root / "task" / IntVar(id) / "complete" =>
        model.complete(Id(id)) match
          case None => NotFound()
          case Some(task) => Ok(task.asJson)

      case GET -> Root / "tasks" =>
        val tasks = model.tasks
        Ok(tasks)

      case GET -> Root / "tasks" / tag =>
        val tasks = model.tasks(Tag(tag))
        Ok(tasks)

      case GET -> Root / "tags" =>
        val tags = model.tags
        Ok(tags)

    }
