package C.w2

class Intersect(e1: Geometry, e2: Geometry) extends GeoExp:
  def evalProg(env: Map[String, GeoVal]): GeoVal =
    e1.evalProg(env) intersect e2.evalProg(env)
  def preprocessProg: Geometry = Intersect(e1.preprocessProg, e2.preprocessProg)

end Intersect
