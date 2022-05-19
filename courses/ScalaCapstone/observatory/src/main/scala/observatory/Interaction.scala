package observatory
import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.pixels.Pixel
import com.sksamuel.scrimage.metadata.ImageMetadata
import scala.collection.parallel.CollectionConverters.given
import scala.math.*                                                      // TODO
import observatory.Visualization.{interpolateColor, predictTemperature}  // TODO

/**
  * 3rd milestone: interactive visualization
  */
object Interaction extends InteractionInterface:
  val pi: Double = Math.PI // TODO

  /**
    * @param tile Tile coordinates
    * @return The latitude and longitude of the top-left corner of the tile, 
    * as per http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
    */
  def tileLocation(tile: Tile): Location =                               // TODO
    val denom: Int = 1 << tile.zoom        // MUST BE Int! Double does not work!
    Location(
      atan(sinh(pi - tile.y / denom * 2 * pi)) * 180 / pi,
      tile.x / denom * 360 - 180
    )

  /**
    * @param temperatures Known temperatures
    * @param colors Color scale
    * @param tile Tile coordinates
    * @return A 256×256 image showing the contents of the given tile
    */
  def tile(temperatures: Iterable[(Location, Temperature)],
                 colors: Iterable[(Temperature, Color)],
                   tile: Tile)
                       : ImmutableImage =                                // TODO
    val (width, height) = (256, 256)
    val alpha = 127

    val pixels: IndexedSeq[(Int, Int)] =
      for
        x <- 0 until height
        y <- 0 until width
      yield (x, y)

    val pixelArray: Array[Pixel] =
      // x,y are passed all the way down b/c stupid Pixel method requires them.
      pixels
        .par
        .map { case (x, y) =>
          (x, y, Tile(y + tile.y * width, x + tile.x * height, tile.zoom + 8)) }
        .map { case (x, y, tile)  => (x, y, tileLocation(tile)) }
        .map { case (x, y, loc) => (x, y, predictTemperature(temperatures, loc)) }
        .map { case (x, y, temp) => (x, y, interpolateColor(colors, temp)) }
        .map { case (x, y, color) =>
          Pixel(x, y, color.red, color.green, color.blue, alpha) }
        .toArray

    ImmutableImage.wrapPixels(width, height, pixelArray, ImageMetadata.empty)

  /**
    * Generates all the tiles for zoom levels 0 to 3 (included), 
    * for all the given years.
    * @param yearlyData Sequence of (year, data), where `data` is some data
    *                associated with `year`. The type of `data` can be anything.
    * @param generateImage Function that generates an image given a year, a zoom 
    *                      level, the x and y coordinates of the tile and the 
    *                      data to build the image from.
    */
  def generateTiles[Data](
    yearlyData: Iterable[(Year, Data)],
    generateImage: (Year, Tile, Data) => Unit
  ): Unit =                                                              // TODO
    for
      zoom <- 0 to 3
      size: Int = 1 << zoom                // MUST BE Int! Double does not work!
      x <- 0 until size
      y <- 0 until size
      (year, data) <- yearlyData
    do generateImage(year, Tile(x, y, zoom), data)
