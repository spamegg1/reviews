package org.epfl.testing

class HttpServerSuite2 extends munit.FunSuite:

  val httpServer = HttpServer()
  
  override def beforeAll(): Unit =
    httpServer.start(8888)

  override def afterAll(): Unit =
    httpServer.stop()

  test("server is running") {
    // Perform HTTP request here
  }

end HttpServerSuite2