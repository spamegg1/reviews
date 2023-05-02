package observatory
import scala.collection.parallel.CollectionConverters.given
import observatory.Visualization.predictTemperature

/** 4th milestone: value-added information
  */
object Manipulation extends ManipulationInterface:

  /** @param temperatures
    *   Known temperatures
    * @return
    *   A function that, given a latitude in [-89, 90] and a longitude in [-180,
    *   179], returns the predicted temperature at this location
    */
  def makeGrid(
      temperatures: Iterable[(Location, Temperature)]
  ): GridLocation => Temperature = // TODO
    // gLoc => predictTemperature(temperatures, Location(gLoc.lat, gLoc.lon))

    // using memoization
    val memo = collection.mutable.Map[GridLocation, Temperature]()
    val fun = (gLoc: GridLocation) =>
      memo.get(gLoc) match
        case None =>
          val res =
            predictTemperature(temperatures, Location(gLoc.lat, gLoc.lon))
          memo += gLoc -> res
          res
        case Some(value) => value
    fun

  /** @param temperaturess
    *   Sequence of known temperatures over the years (each element of the
    *   collection is a collection of pairs of location and temperature)
    * @return
    *   A function that, given a latitude and a longitude, returns the average
    *   temperature at this location
    */
  def average(
      temperaturess: Iterable[Iterable[(Location, Temperature)]]
  ): GridLocation => Temperature = // TODO
    gridLoc =>
      val temps: Iterable[Temperature] =
        temperaturess
          .map(makeGrid)
          .map(fun => fun(gridLoc))
      temps.sum / temps.size

  /** @param temperatures
    *   Known temperatures
    * @param normals
    *   A grid containing the “normal” temperatures
    * @return
    *   A grid containing deviations compared to the normal temperatures
    */
  def deviation(
      temperatures: Iterable[(Location, Temperature)],
      normals: GridLocation => Temperature
  ): GridLocation => Temperature = // TODO
    // gridLoc => makeGrid(temperatures)(gridLoc) - normals(gridLoc)
    val fun = makeGrid(temperatures) // use memoization!
    val devFun = (gLoc: GridLocation) => fun(gLoc) - normals(gLoc)
    devFun
