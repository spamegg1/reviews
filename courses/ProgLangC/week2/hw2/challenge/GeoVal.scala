package C.w2

/*  companion object for useful helper methods */
object GeoVal:
  val epsilon = 0.00001
  def realClose(r1: Double, r2: Double): Boolean = (r1 - r2).abs < epsilon

  def realClosePoint(
      x1: Double,
      y1: Double,
      x2: Double,
      y2: Double
  ): Boolean =
    realClose(x1, x2) && realClose(y1, y2)

  def twoPointsToLine(
      x1: Double,
      y1: Double,
      x2: Double,
      y2: Double
  ): GeoVal =
    if realClose(x1, x2)
    then VerticalLine(x1)
    else
      val m = (y2 - y1) / (x2 - x1)
      val b = y1 - m * x1
      Line(m, b)

  def inBetween(v: Double, end1: Double, end2: Double): Boolean =
    val a = end1 - epsilon <= v
    val b = v <= epsilon + end2
    val c = end2 - epsilon <= v
    val d = v <= epsilon + end1
    (a && b) || (c && d)

abstract class GeoVal extends Geometry:
  /*  these 6 methods need to be implemented by the 5 subclasses */
  def shift(dx: Double, dy: Double): GeoVal
  def intersect(that: GeoVal): GeoVal
  def intersectPoint(that: Point): GeoVal
  def intersectLine(that: Line): GeoVal
  def intersectVerticalLine(that: VerticalLine): GeoVal
  def intersectWithSegmentAsLineResult(that: LineSegment): GeoVal

  def intersectLineSegment(that: LineSegment): GeoVal =
    val line = GeoVal.twoPointsToLine(that.x1, that.y1, that.x2, that.y2)
    intersect(line).intersectWithSegmentAsLineResult(that)

  def intersectNoPoints(that: GeoVal): GeoVal = that // NoPoints

  def evalProg(env: Map[String, GeoVal]): GeoVal = this
  def preprocessProg: Geometry = this

end GeoVal
