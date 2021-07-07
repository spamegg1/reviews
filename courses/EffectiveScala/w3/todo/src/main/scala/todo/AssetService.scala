package todo

import cats.effect.*
import org.http4s.HttpRoutes
import org.http4s.server.staticcontent.*

/**
 * This service is responsible for serving static files. We serve anything found
 * in the src/main/resources/todo subdirectory, which includes the user
 * interface.
 *
 * You should NOT modify this file.
 */
object AssetService:
  def service(blocker: Blocker)(using cs: ContextShift[IO]): HttpRoutes[IO] =
    fileService(FileService.Config("src/main/resources/todo/", blocker))
