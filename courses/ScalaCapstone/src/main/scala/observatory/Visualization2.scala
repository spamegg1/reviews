package observatory
import com.sksamuel.scrimage.{Image, Pixel}
import observatory.Interaction.tileLocation
import observatory.Visualization.interpolateColor
import scala.collection.parallel.ParSeq
import scala.math._

object Visualization2 extends Visualization2Interface {

  /**
    * @param point (x, y) coordinates of a point in the grid cell
    * @param d00 Top-left value
    * @param d01 Bottom-left value
    * @param d10 Top-right value
    * @param d11 Bottom-right value
    * @return A guess of the value at (x, y) based on the four known values,
    *         using bilinear interpolation
    *       See https://en.wikipedia.org/wiki/Bilinear_interpolation#Unit_Square
    */
  def bilinearInterpolation(point: CellPoint,
                            d00: Temperature,
                            d01: Temperature,
                            d10: Temperature,
                            d11: Temperature): Temperature =
    d00 * (1 - point.x) * (1 - point.y) +
    d10 *      point.x  * (1 - point.y) +
    d01 * (1 - point.x) *      point.y  +
    d11 *      point.x  *      point.y

  /**
    * @param grid Grid to visualize
    * @param colors Color scale to use
    * @param tile Tile coordinates to visualize
    * @return The image of the tile at (x, y, zoom)
    *         showing the grid using the given color scale
    * NOTE: COPIED FROM Visualization.visualize(),
    * modified to use gridLocations and bilinearInterpolation
    */
  def visualizeGrid(grid  : GridLocation => Temperature,
                    colors: Iterable[(Temperature, Color)],
                    tile  : Tile): Image = {
    val pixels: IndexedSeq[(Int, Int)] =
      for {
        x <- 0 until 256
        y <- 0 until 256
      } yield (x, y)

    val locations: ParSeq[Location] =
      pixels
        .par
        .map { case (x, y) =>
          Tile(y + tile.y * 256, x + tile.x * 256, tile.zoom + 8) }
        .map(tileLocation)

    val predictedTemps: ParSeq[Temperature] =
      locations.map { case Location(lat, lon) =>
        val x0: Int = floor(lon).toInt
        val x1: Int =  ceil(lon).toInt
        val y0: Int =  ceil(lat).toInt
        val y1: Int = floor(lat).toInt
        val d00: Temperature = grid(GridLocation(y0, x0))
        val d01: Temperature = grid(GridLocation(y1, x0))
        val d10: Temperature = grid(GridLocation(y0, x1))
        val d11: Temperature = grid(GridLocation(y1, x1))
        bilinearInterpolation(CellPoint(lon - x0, y0 - lat), d00, d01, d10, d11)
    }

    val coloredLocations: ParSeq[Color] =
      predictedTemps.map(temp => interpolateColor(colors, temp))

    val pixelArray: Array[Pixel] = coloredLocations
      .map(color => Pixel(color.red, color.green, color.blue, 127))
      .toArray

    Image(256, 256, pixelArray)
  }

}
