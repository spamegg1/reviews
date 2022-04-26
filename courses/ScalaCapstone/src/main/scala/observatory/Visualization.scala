package observatory

import com.sksamuel.scrimage.{Image, Pixel}

import scala.collection.parallel.ParSeq
import scala.math._

/**
  * 2nd milestone: basic visualization
  *
  * Your records contain the average temperature over a year, for each station’s
  * location. Your work consists in building an image of 360×180 pixels,
  * where each pixel shows the temperature at its location.
  * The point at latitude 0 and longitude 0
  * (the intersection between the Greenwich meridian and the equator)
  * will be at the center of the image.
  *
  * In this figure, the red crosses represent the weather stations.
  * As you can see, you will have to spatially interpolate the data in order to
  * guess the temperature corresponding to the location of each pixel
  * (such a pixel is represented by a green square in the picture).
  * Then you will have to convert this temperature value into
  * a pixel color based on a color scale.
  *
  * This color scale means that
  * a temperature of 60°C or above should be represented in
  *    white (255, 255, 255, 255),
  * a temperature of 32°C should be represented in red (255, 0, 0, 255),
  * a temperature of 12°C should be represented in yellow (255, 255, 0, 255),
  * and so on.
  * For temperatures between thresholds, say, between 12°C and 32°C, you will
  * have to compute a linear interpolation between the yellow and red colors.
  *
  * Here are the RGB values of these colors:
  * Temperature (°C) Red Green Blue
  *  60 255 255 255
  *  32 255   0   0
  *  12 255 255   0
  *   0   0 255 255
  * -15   0   0 255
  * -27 255   0 255
  * -50  33   0 107
  * -60   0   0   0
  *
  * *********************
  * Spatial interpolation
  * *********************
  * You will have to implement the method predictTemperature.
  * This method takes a sequence of known temperatures at the given locations,
  * and a location where we want to guess the temperature,
  * and returns an estimate based on the inverse distance weighting algorithm:
  *     https://en.wikipedia.org/wiki/Inverse_distance_weighting
  * (you can use any p value greater or equal to 2;
  * try and use whatever works best for you!).
  * To approximate distance between two locations, we suggest
  * you use great-circle distance formula:
  *     https://en.wikipedia.org/wiki/Great-circle_distance
  *
  * Note that the great-circle distance formula is known to have
  * rounding errors for short distances
  * (a few meters), but that’s not a problem for us because we don’t need
  * such a high degree of precision.
  * Thus, you can use the first formula given on the Wikipedia page,
  * expanded to cover some edge cases like equal locations and antipodes:
  *     deltaSigma = 0 for equal points, pi for antipodes, and
  * arccos(sin(lat1) * sin(lat2) + cos(lat1) * cos(lat2) * cos(abs(lon1 - lon2)))
  *     dist = r * deltaSigma
  *
  * However, running the inverse distance weighting algorithm with small
  * distances will result in huge numbers (since we divide by the distance
  * raised to the power of p),
  * which can be a problem. A solution to this problem is to directly use the
  * known temperature of the close (less than 1 km) location as a prediction.
  *
  * ********************
  * Linear interpolation
  * ********************
  * We're providing you with a simple case class for representing color;
  * you can see Color's documentation in models.scala for more information.
  *
  *     case class Color(red: Int, green: Int, blue: Int)
  *
  * You will have to implement the method interpolateColor.
  * This method takes a sequence of reference temperature values and their
  * associated color, and a temperature value, and returns an estimate of
  * the color corresponding to the given value,
  * by applying a linear interpolation algorithm:
  *     https://en.wikipedia.org/wiki/Linear_interpolation
  * Note that the given points are not sorted in a particular order.
  *
  * *************
  * Visualization
  * *************
  * Once you have completed the above steps you can implement the visualize
  * method to build an image (using the scrimage library) where each pixel shows
  * the temperature corresponding to its location.
  * Note that the (x,y) coordinates of the top-left pixel is (0,0)
  * and then the x axis grows to the right and the y axis grows to the bottom,
  * to (359,179),
  * whereas the latitude and longitude origin, (0,0), is at the center of the 
  * image (180,90),
  * and the top-left pixel (0,0) has GPS coordinates (90, -180).
  * and the top-right pixel (359,0) has GPS coordinates (90, 179).
  * and the bottom-left pixel (0,179) has GPS coordinates (-89, -180).
  * and the bottom-right pixel (359,179) has GPS coordinates (-89, 179).
  *
  * So... 
  * pixel's x location goes from 0 to 359... 
  * where longitude goes from -180 to 179
  *      this means that longitude = x - 180
  * pixel's y location goes from 0 to 179... 
  * where latitude goes from 90 to -89
  *      this means that latitude = 90 - y
  *
  * ******************************
  * Appendix: scrimage cheat sheet
  * ******************************
  * Here is a description of scrimage’s API parts that are relevant for you.
  *
  * Image type and companion object.
  * A simple way to construct an image is to use constructors
  * that take an Array[Pixel] as parameter.
  * In such a case, the array must contain exactly width × height elements,
  * in the following order:
  * the first element is the top-left pixel,
  * followed by all the pixels of the top row,
  * followed by the other rows.
  * A simple way to construct a pixel from RGB values is to use this constructor.
  * To write an image into a PNG file, use the output method.
  * For instance: myImage.output(new java.io.File("target/some-image.png")).
  * To check that some predicate holds for all the pixels of an image,
  * use the forall method.
  * Also, note that scrimage defines a Color type, which could be ambiguous
  * with our Color definition.
  * Beware to not import scrimage’s Color.
  */
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
