package reductions

class LineOfSightSuite extends munit.FunSuite:
  import LineOfSight.*

  test("lineOfSight should correctly handle an array of size 4") {
    val output = new Array[Float](4)
    lineOfSight(Array[Float](0f, 1f, 8f, 9f), output)
    assertEquals(output.toList, List(0f, 1f, 4f, 4f))
  }

  test("upsweepSequential should correctly handle the chunk 1 until 4 of a 4-elt array") {
    val res = upsweepSequential(Array[Float](0f, 1f, 8f, 9f), 1, 4)
    assertEquals(res, 4f)
  }

  test("downsweepSequential should correctly handle a 4 element array with 0 angle") {
    val output = new Array[Float](4)
    downsweepSequential(Array[Float](0f, 1f, 8f, 9f), output, 0f, 1, 4)
    assertEquals(output.toList, List(0f, 1f, 4f, 4f))
  }

  test("parLineOfSight should correctly handle an array of size 4 and threshold 4") {
    val output = new Array[Float](4)
    parLineOfSight(Array[Float](0f, 1f, 8f, 9f), output, 4)
    assertEquals(output.toList, List(0f, 1f, 4f, 4f))
  }

  test("parLineOfSight should correctly handle an array of size 4 and threshold 1") {
    val output = new Array[Float](4)
    parLineOfSight(Array[Float](0f, 1f, 8f, 9f), output, 1)
    assertEquals(output.toList, List(0f, 1f, 4f, 4f))
  }

  import scala.concurrent.duration.*
  override val munitTimeout = 10.seconds
