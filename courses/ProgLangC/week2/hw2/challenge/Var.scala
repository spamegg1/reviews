package C.w2

class Var(s: String) extends GeoExp:
  def evalProg(env: Map[String, GeoVal]): GeoVal =
    env.get(s) match
      case Some(geoVal) => geoVal
      case None => throw new NoSuchElementException("undefined variable")

  def preprocessProg: Geometry = this

end Var
