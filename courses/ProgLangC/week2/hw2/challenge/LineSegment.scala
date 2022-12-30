package C.w2

class LineSegment(
    var x1: Double,
    var y1: Double,
    var x2: Double,
    var y2: Double
) extends GeoVal:
  override def preprocessProg: GeoVal =
    if GeoVal.realClosePoint(x1, y1, x2, y2) then Point(x1, y1)
    else if GeoVal.realClose(x1, x2) && y2 < y1 then LineSegment(x2, y2, x1, y1)
    else if GeoVal.realClose(x1, x2) && y1 <= y2 then this
    else if x2 < x1 then LineSegment(x2, y2, x1, y1)
    else this

  def shift(dx: Double, dy: Double): GeoVal =
    LineSegment(x1 + dx, y1 + dy, x2 + dx, y2 + dy)
  def intersect(that: GeoVal): GeoVal = that intersectLineSegment this
  def intersectPoint(that: Point): GeoVal = that intersectLineSegment this
  def intersectLine(that: Line): GeoVal = that intersectLineSegment this
  def intersectVerticalLine(that: VerticalLine): GeoVal =
    that intersectLineSegment this

  def intersectWithSegmentAsLineResult(that: LineSegment): GeoVal =
    if GeoVal.realClose(x1, x2) then // segments are on a vertical line
      // let a start at b, or below b
      val a = if y1 < that.y1 then this else that
      val b = if y1 < that.y1 then that else this

      if GeoVal.realClose(a.y2, b.y1) then Point(a.x2, a.y2) // just touching
      else if a.y2 < b.y1 then NoPoints // disjoint
      else if b.y2 < a.y2 then b // b inside a
      else LineSegment(b.x1, b.y1, a.x2, a.y2) // overlapping
    else // the segments are on a (non-vertical) line
      // let a start at b, or to the left of b
      val a = if x1 < that.x1 then this else that
      val b = if x1 < that.x1 then that else this

      if GeoVal.realClose(a.x2, b.x1) then Point(a.x2, a.y2) // just touching
      else if a.x2 < b.x1 then NoPoints // disjoint
      else if b.x2 < a.x2 then b // b inside a
      else LineSegment(b.x1, b.y1, a.x2, a.y2) // overlapping

end LineSegment
