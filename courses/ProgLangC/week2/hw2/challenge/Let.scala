package C.w2

class Let(s: String, e1: Geometry, e2: Geometry) extends GeoExp:
  def evalProg(env: Map[String, GeoVal]): GeoVal =
    e2.evalProg(env + (s -> e1.evalProg(env)))
  def preprocessProg: Geometry = Let(s, e1.preprocessProg, e2.preprocessProg)

end Let
