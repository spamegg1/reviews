package observatory

object Manipulation extends ManipulationInterface {

  /**
    * @param temperatures Known temperatures
    * @return A function that, given a  latitude in [-89, 90]
    *                            and a longitude in [-180, 179],
    *         returns the predicted temperature at this location
    */
  def makeGrid(temperatures: Iterable[(Location, Temperature)])
                           : GridLocation => Temperature =
    gridLoc => Visualization.predictTemperature(
      temperatures, Location(gridLoc.lat, gridLoc.lon))

  /**
    * @param temperatures Sequence of known temperatures over the years
    *                     (each element of the collection
    *                     is a collection of pairs of location and temperature)
    * @return A function that, given a latitude and a longitude,
    *         returns the average temperature at this location
    */
  def average(temperatures: Iterable[Iterable[(Location, Temperature)]])
    : GridLocation => Temperature = { gridLoc =>
      val temps: Iterable[Temperature] =
        temperatures
          .map(makeGrid)
          .map(fun => fun(gridLoc))
      temps.sum / temps.size
  }

  /**
    * @param temperatures Known temperatures
    * @param normals A grid containing the “normal” temperatures
    * @return A grid containing deviations compared to the normal temperatures
    */
  def deviation(temperatures: Iterable[(Location, Temperature)],
                normals: GridLocation => Temperature)
              : GridLocation => Temperature =
    gridLoc => makeGrid(temperatures)(gridLoc) - normals(gridLoc)
}
