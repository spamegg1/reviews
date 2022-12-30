package C.w2

class VerticalLine(var x: Double) extends GeoVal:
  def shift(dx: Double, dy: Double): GeoVal = VerticalLine(x + dx)
  def intersect(that: GeoVal): GeoVal = that intersectVerticalLine this
  def intersectPoint(p: Point): GeoVal = p intersectVerticalLine this
  def intersectLine(line: Line): GeoVal = line intersectVerticalLine this
  def intersectVerticalLine(vLine: VerticalLine): GeoVal =
    if GeoVal.realClose(x, vLine.x) then this else NoPoints
  def intersectWithSegmentAsLineResult(that: LineSegment): GeoVal = that

end VerticalLine
