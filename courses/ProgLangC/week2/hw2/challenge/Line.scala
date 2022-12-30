package C.w2

class Line(var m: Double, var b: Double) extends GeoVal:
  def shift(dx: Double, dy: Double): GeoVal = Line(m, b + dy - m * dx)
  def intersect(that: GeoVal): GeoVal = that intersectLine this
  def intersectPoint(p: Point): GeoVal = p intersectLine this
  def intersectLine(line: Line): GeoVal =
    if GeoVal.realClose(m, line.m) then
      if GeoVal.realClose(b, line.b) then this else NoPoints
    else // one point intersection
      val x = (line.b - b) / (m - line.m)
      val y = m * x + b
      Point(x, y)

  def intersectVerticalLine(vLine: VerticalLine): GeoVal =
    Point(vLine.x, m * vLine.x + b)
  def intersectWithSegmentAsLineResult(that: LineSegment): GeoVal = that

end Line
