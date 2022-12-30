package C.w2

class Point(var x: Double, var y: Double) extends GeoVal:
  def shift(dx: Double, dy: Double): GeoVal = Point(x + dx, y + dy)
  def intersect(that: GeoVal): GeoVal = that intersectPoint this
  def intersectPoint(p: Point): GeoVal =
    if GeoVal.realClosePoint(x, y, p.x, p.y) then this else NoPoints
  def intersectLine(line: Line): GeoVal =
    if GeoVal.realClose(y, line.m * x + line.b) then this else NoPoints
  def intersectVerticalLine(vLine: VerticalLine): GeoVal =
    if GeoVal.realClose(x, vLine.x) then this else NoPoints
  def intersectWithSegmentAsLineResult(seg: LineSegment): GeoVal =
    if GeoVal.inBetween(x, seg.x1, seg.x2) &&
      GeoVal.inBetween(y, seg.y1, seg.y2)
    then this
    else NoPoints

end Point
