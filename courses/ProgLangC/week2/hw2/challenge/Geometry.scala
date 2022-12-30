package C.w2

/*  these two need to be implemented by both GeoVal and GeoExp */
trait Geometry:
  /*  GeoVals preprocess to themselves, GeoExps get "simplified" to GeoExps */
  def preprocessProg: Geometry

  /* both GeoExps and GeoVals get "evaluated" to GeoVals
     by looking up GeoVals in an environment. */
  def evalProg(env: Map[String, GeoVal]): GeoVal
