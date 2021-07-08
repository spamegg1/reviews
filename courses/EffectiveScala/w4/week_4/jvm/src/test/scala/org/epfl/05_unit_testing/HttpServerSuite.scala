package org.epfl.testing

class HttpServerSuite extends munit.FunSuite:

  val withHttpServer = FunFixture[HttpServer](
    setup = _ => {
      val httpServer = HttpServer()
      httpServer.start(8888)
      httpServer
    },
    teardown = httpServer => httpServer.stop()
  )

  withHttpServer.test("server is running") { _ =>
    // Perform HTTP request here
  }

end HttpServerSuite
