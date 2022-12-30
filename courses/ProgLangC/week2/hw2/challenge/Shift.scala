package C.w2

class Shift(dx: Double, dy: Double, e: Geometry) extends GeoExp:
  def evalProg(env: Map[String, GeoVal]): GeoVal =
    e.evalProg(env).shift(dx, dy)
  def preprocessProg: Geometry = Shift(dx, dy, e.preprocessProg)

end Shift
