package observatory
import com.sksamuel.scrimage.{Image, Pixel}
import observatory.Visualization.{interpolateColor, predictTemperature}
import scala.math._


object Interaction extends InteractionInterface {
  val pi: Double = Math.PI

  /**
    * @param tile Tile coordinates
    * @return The latitude and longitude of the top-left corner of the tile,
    *         as per http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
    */
  def tileLocation(tile: Tile): Location = {
    val denom: Int = 1 << tile.zoom        // MUST BE Int! Double does not work!
    Location(
      atan(sinh(pi - tile.y / denom * 2 * pi)) * 180 / pi,
      tile.x / denom * 360 - 180
    )
  }

  /**
    * @param temperatures Known temperatures
    * @param colors Color scale
    * @param tile Tile coordinates
    * @return A 256×256 image showing the contents of the given tile
    * We recommend using an alpha value of 127.
    * Hint: you will have to compute the corresponding latitude and longitude
    * of each pixel within a tile.
    * A simple way to achieve that is to rely on the fact that each pixel in a
    * tile can be thought of
    * a subtile at a higher zoom level (256 = 2⁸).
    */
  def tile(temperatures: Iterable[(Location, Temperature)],
                 colors: Iterable[(Temperature, Color)],
                   tile: Tile): Image = {
    val pixels: IndexedSeq[(Int, Int)] =
      for {
        x <- 0 until 256
        y <- 0 until 256
      } yield (x, y)

    val pixelArray: Array[Pixel] =
      pixels
        .par
        .map { case (x, y) =>
          Tile(y + tile.y * 256, x + tile.x * 256, tile.zoom + 8) }
        .map(tileLocation)
        .map(loc => (loc, predictTemperature(temperatures, loc)))
        .map { case (_, temp) => interpolateColor(colors, temp) }
        .map(color => Pixel(color.red, color.green, color.blue, 127))
        .toArray

    Image(256, 256, pixelArray)
  }

  /**
    * Generates all the tiles for zoom levels 0 to 3 (included),
    * for all the given years.
    * @param yearlyData Sequence of (year, data), where `data` is some data
    *                   associated with `year`.
    *                   The type of `data` can be anything.
    * @param generateImage Function that generates an image given a year,
    *                      a zoom level, the x and y coordinates of the tile
    *                      and the data to build the image from.
    */
  def generateTiles[Data](yearlyData: Iterable[(Year, Data)],
                          generateImage: (Year, Tile, Data) => Unit)
                          : Unit =
    for {
      zoom <- 0 to 3
      size: Int = 1 << zoom                // MUST BE Int! Double does not work!
      x <- 0 until size
      y <- 0 until size
      (year, data) <- yearlyData
    } generateImage(year, Tile(x, y, zoom), data)
}
