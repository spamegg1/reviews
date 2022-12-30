package C.w2

object NoPoints extends GeoVal:
  def shift(dx: Double, dy: Double): GeoVal = this
  def intersect(that: GeoVal): GeoVal = that intersectNoPoints this
  def intersectPoint(p: Point): GeoVal = this
  def intersectLine(line: Line): GeoVal = this
  def intersectVerticalLine(vLine: VerticalLine): GeoVal = this
  def intersectWithSegmentAsLineResult(that: LineSegment): GeoVal = this

end NoPoints
