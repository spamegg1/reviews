package C.w2

/**
  * This is an outline of hw7 structure.
  * I added "Interface" to all the names to avoid name clashes.
  * Also added "abstract" modifier to classes to leave them unimplemented.
  */

trait GeoInterface:
  def preprocessProg: GeoInterface
  def evalProg(env: Map[String, GeoValInterface]): GeoValInterface

object GeoValInterface:
  val epsilon = 0.00001
  def realClose(r1: Double, r2: Double): Boolean = ???
  def realClosePoint(
      x1: Double,
      y1: Double,
      x2: Double,
      y2: Double
  ): Boolean = ???
  def twoPointsToLine(
      x1: Double,
      y1: Double,
      x2: Double,
      y2: Double
  ): GeoVal = ???

trait GeoValInterface extends GeoInterface:
  def shift(dx: Double, dy: Double): GeoValInterface
  def intersect(that: GeoValInterface): GeoValInterface
  def intersectNoPoints(that: GeoValInterface): GeoValInterface
  def intersectPoint(that: PointInterface): GeoValInterface
  def intersectLine(that: LineInterface): GeoValInterface
  def intersectVerticalLine(that: VerticalLineInterface): GeoValInterface
  def intersectLineSegment(that: LineSegmentInterface): GeoValInterface
  def intersectWithSegmentAsLineResult(
      that: LineSegmentInterface
  ): GeoValInterface

abstract class NoPointsInterface extends GeoValInterface
abstract class PointInterface(x: Double, y: Double) extends GeoValInterface
abstract class LineInterface(m: Double, b: Double) extends GeoValInterface
abstract class VerticalLineInterface(x: Double) extends GeoValInterface
abstract class LineSegmentInterface(
    x1: Double,
    y1: Double,
    x2: Double,
    y2: Double
) extends GeoValInterface

trait GeoExpInterface extends GeoInterface

abstract class IntersectInterface(
    e1: GeoInterface,
    e2: GeoInterface
) extends GeoExpInterface
abstract class LetInterface(
    s: String,
    e1: GeoInterface,
    e2: GeoInterface
) extends GeoExpInterface
abstract class VarInterface(s: String) extends GeoExpInterface
abstract class ShiftInterface(
    dx: Double,
    dy: Double,
    e: GeoInterface
) extends GeoExpInterface
