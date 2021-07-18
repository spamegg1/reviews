package barneshut

import java.util.concurrent.*
import scala.collection.*
import scala.math.*
import scala.collection.parallel.*
import barneshut.conctrees.ConcBuffer

class BarnesHutSuite extends munit.FunSuite:
  // test cases for quad tree

  import FloatOps.*
  test("Empty: center of mass should be the center of the cell") {
    val quad = Empty(51f, 46.3f, 5f)
    assert(quad.massX == 51f, s"${quad.massX} should be 51f")
    assert(quad.massY == 46.3f, s"${quad.massY} should be 46.3f")
  }

  test("Empty: mass should be 0") {
    val quad = Empty(51f, 46.3f, 5f)
    assert(quad.mass == 0f, s"${quad.mass} should be 0f")
  }

  test("Empty: total should be 0") {
    val quad = Empty(51f, 46.3f, 5f)
    assert(quad.total == 0, s"${quad.total} should be 0")
  }

  test("Leaf with 1 body") {
    val b = Body(123f, 18f, 26f, 0f, 0f)
    val quad = Leaf(17.5f, 27.5f, 5f, Seq(b))

    assert(quad.mass ~= 123f, s"${quad.mass} should be 123f")
    assert(quad.massX ~= 18f, s"${quad.massX} should be 18f")
    assert(quad.massY ~= 26f, s"${quad.massY} should be 26f")
    assert(quad.total == 1, s"${quad.total} should be 1")
  }


  test("Fork with 3 empty quadrants and 1 leaf (nw)") {
    val b = Body(123f, 18f, 26f, 0f, 0f)
    val nw = Leaf(17.5f, 27.5f, 5f, Seq(b))
    val ne = Empty(22.5f, 27.5f, 5f)
    val sw = Empty(17.5f, 32.5f, 5f)
    val se = Empty(22.5f, 32.5f, 5f)
    val quad = Fork(nw, ne, sw, se)

    assert(quad.centerX == 20f, s"${quad.centerX} should be 20f")
    assert(quad.centerY == 30f, s"${quad.centerY} should be 30f")
    assert(quad.mass ~= 123f, s"${quad.mass} should be 123f")
    assert(quad.massX ~= 18f, s"${quad.massX} should be 18f")
    assert(quad.massY ~= 26f, s"${quad.massY} should be 26f")
    assert(quad.total == 1, s"${quad.total} should be 1")
  }

  test("Empty.insert(b) should return a Leaf with only that body (2pts)") {
    val quad = Empty(51f, 46.3f, 5f)
    val b = Body(3f, 54f, 46f, 0f, 0f)
    val inserted = quad.insert(b)
    inserted match
      case Leaf(centerX, centerY, size, bodies) =>
        assert(centerX == 51f, s"$centerX should be 51f")
        assert(centerY == 46.3f, s"$centerY should be 46.3f")
        assert(size == 5f, s"$size should be 5f")
        assert(bodies == Seq(b), s"$bodies should contain only the inserted body")
      case _ =>
        fail("Empty.insert() should have returned a Leaf, was $inserted")
  }

  // test cases for Body

  test("Body.updated should do nothing for Empty quad trees") {
    val b1 = Body(123f, 18f, 26f, 0f, 0f)
    val body = b1.updated(Empty(50f, 60f, 5f))

    assertEquals(body.xspeed, 0f, precisionThreshold)
    assertEquals(body.yspeed, 0f, precisionThreshold)
  }

  test("Body.updated should take bodies in a Leaf into account (2pts)") {
    val b1 = Body(123f, 18f, 26f, 0f, 0f)
    val b2 = Body(524.5f, 24.5f, 25.5f, 0f, 0f)
    val b3 = Body(245f, 22.4f, 41f, 0f, 0f)

    val quad = Leaf(15f, 30f, 20f, Seq(b2, b3))

    val body = b1.updated(quad)

    assert(body.xspeed ~= 12.587037f)
    assert(body.yspeed ~= 0.015557117f)
  }

  // test cases for sector matrix

  test("'SectorMatrix.+=' should add a body at (25,47) to the correct bucket of a sector matrix of size 96 (2pts)") {
    val body = Body(5, 25, 47, 0.1f, 0.1f)
    val boundaries = Boundaries()
    boundaries.minX = 1
    boundaries.minY = 1
    boundaries.maxX = 97
    boundaries.maxY = 97
    val sm = SectorMatrix(boundaries, SECTOR_PRECISION)
    sm += body
    val res = sm(2, 3).size == 1 && sm(2, 3).find(_ == body).isDefined
    assert(res, s"Body not found in the right sector")
  }

  import scala.concurrent.duration.*
  override val munitTimeout = 10.seconds

object FloatOps:
  val precisionThreshold = 1e-4

  /** Floating comparison: assert(float ~= 1.7f). */
  extension (self: Float) def ~=(that: Float): Boolean =
    abs(self - that) < precisionThreshold

  /** Long floating comparison: assert(double ~= 1.7). */
  extension (self: Double) def ~=(that: Double): Boolean =
    abs(self - that) < precisionThreshold

  /** Floating sequences comparison: assert(floatSeq ~= Seq(0.5f, 1.7f). */
  extension (self: Seq[Float]) def ~=(that: Seq[Float]): Boolean =
    self.size == that.size &&
      self.zip(that).forall { case (a, b) =>
        abs(a - b) < precisionThreshold
      }

