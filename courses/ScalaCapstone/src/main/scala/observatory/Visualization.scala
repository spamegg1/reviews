package observatory
import com.sksamuel.scrimage.{Image, Pixel}
import scala.collection.parallel.ParSeq
import scala.math._

object Visualization extends VisualizationInterface {
  val earthRadius: Double = 6371.0                              // in kilometers
  val radians: Double = Math.PI / 180.0
  val p: Int = 2

  /**
    * @param temperatures Known temperatures: pairs containing a location
    *                     and the temperature at this location
    * @param location Location where to predict the temperature
    * @return The predicted temperature at `location`
    */
  def predictTemperature(temperatures: Iterable[(Location, Temperature)],
                         location: Location): Temperature =
    if (temperatures.exists(_._1 == location))   // location = one of given locs
      temperatures
        .find(_._1 == location)
        .get
        ._2
    else {
      val distances: Iterable[(Double, Temperature)] =
        temperatures.map { case (oLoc, oTemp) => (dist(location, oLoc), oTemp) }

      distances.find(_._1 < 1.0) match {
        case Some((_, temp)) => temp  // small dist, use known temp of close loc
        case None =>                                    // all distances are > 1
          val weights: Iterable[Double] =
            distances
              .map(_._1)
              .map(pow(_, -p))

          val numer: Double =
            weights
              .zip(temperatures.map(_._2))
              .map { case (w, t) => w * t }
              .sum

          val denom: Double = weights.sum

          numer / denom
      }
    }

  /**
    * @param loc  Location where to predict the temperature
    * @param oLoc A given location where temperature is known
    * @return the great circle distance between two locations
    * */
  def dist(loc: Location, oLoc: Location): Double =
    if (loc == oLoc) 0.0
    else if (isAntipode(loc, oLoc)) earthRadius * Math.PI
    else {
      val deltaLon: Double = abs(loc.lon - oLoc.lon)
      earthRadius * acos(
        sin(loc.lat * radians) * sin(oLoc.lat * radians) +
        cos(loc.lat * radians) * cos(oLoc.lat * radians) * cos(deltaLon * radians)
      )
    }

  /**
    * @param loc  Location where to predict the temperature
    * @param oLoc A given location where temperature is known
    * @return true if given locations are antipodes, false otherwise
    * */
  def isAntipode(loc: Location, oLoc: Location): Boolean =
    loc.lat == -oLoc.lat &&
    (abs(loc.lon - oLoc.lon) == 0.0 ||
     abs(loc.lon - oLoc.lon) == 180.0)

  /**
    * @param points Pairs containing a value and its associated color
    * @param value The value to interpolate
    * @return The color that corresponds to `value`,
    *         according to the color scale defined by `points`
    */
  def interpolateColor(points: Iterable[(Temperature, Color)],
                       value: Temperature): Color = {
    val sortedPts: List[(Temperature, Color)] =
      points
        .toList
        .sortBy(_._1)

    if (sortedPts.exists(_._1 == value))
      sortedPts
        .find(_._1 == value)
        .get
        ._2
    else if (sortedPts.last._1 < value) sortedPts.last._2
    else if (sortedPts.head._1 > value) sortedPts.head._2
    else {
      val (less, more) = sortedPts.partition(_._1 < value)
      val (lower, upper) = (less.last, more.head)
      val red  : Double =
        lineSeg(lower._1, lower._2.red, upper._1, upper._2.red, value)
      val green: Double =
        lineSeg(lower._1, lower._2.green, upper._1, upper._2.green, value)
      val blue : Double =
        lineSeg(lower._1, lower._2.blue, upper._1, upper._2.blue, value)

      Color(red.round.toInt, green.round.toInt, blue.round.toInt)
    }
  }

  /**
    * @param x0, y0, x1, y1, x, y
    * @return the interpolated y-value on the line segment (x0, y0) -> (x1, y1)
    * */
  def lineSeg(x0: Double, y0: Double, x1: Double, y1: Double, x: Double): Double =
    y0 + (x - x0) * (y1 - y0) / (x1 - x0)

  /**
    * @param temperatures Known temperatures
    * @param colors Color scale
    * @return A 360×180 image where each pixel shows
    *         the predicted temperature at its location
    */
  def visualize(temperatures: Iterable[(Location, Temperature)],
                colors: Iterable[(Temperature, Color)]): Image = {
    val pixels: IndexedSeq[(Int, Int)] =
      for {
        x <- 0 until 180                                   // HAD TO SWAP THESE!
        y <- 0 until 360
      } yield (x, y)

    val locations: ParSeq[Location] = pixels
      .par
      .map { case (x, y) => Location(90 - x, y - 180) }    // HAD TO SWAP THESE!

    val predictedTemps: ParSeq[(Location, Temperature)] =
      locations.map(loc => (loc, predictTemperature(temperatures, loc)))

    val coloredLocations: ParSeq[Color] = predictedTemps
      .map { case (_, temp) => interpolateColor(colors, temp) }

    val pixelArray: Array[Pixel] = coloredLocations
      .map(color => Pixel(color.red, color.green, color.blue, 255))
      .toArray

    Image(360, 180, pixelArray)
  }
}
