package observatory
import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.pixels.Pixel
import com.sksamuel.scrimage.metadata.ImageMetadata
import scala.collection.parallel.CollectionConverters.given              // TODO
import observatory.Interaction.tileLocation                              // TODO
import observatory.Visualization.interpolateColor                        // TODO
import scala.collection.parallel.ParSeq                                  // TODO
import scala.math.*                                                      // TODO

/**
  * 5th milestone: value-added information visualization
  */
object Visualization2 extends Visualization2Interface:

  /**
    * @param point (x, y) coordinates of a point in the grid cell
    * @param d00 Top-left value
    * @param d01 Bottom-left value
    * @param d10 Top-right value
    * @param d11 Bottom-right value
    * @return A guess of the value at (x, y) based on the four known values, 
    *         using bilinear interpolation
    *      See https://en.wikipedia.org/wiki/Bilinear_interpolation#Unit_Square
    */
  def bilinearInterpolation(
    point: CellPoint,
    d00: Temperature,
    d01: Temperature,
    d10: Temperature,
    d11: Temperature
  ): Temperature =                                                       // TODO
    d00 * (1 - point.x) * (1 - point.y) +
    d10 *      point.x  * (1 - point.y) +
    d01 * (1 - point.x) *      point.y  +
    d11 *      point.x  *      point.y

  /**
    * @param grid Grid to visualize
    * @param colors Color scale to use
    * @param tile Tile coordinates to visualize
    * @return The image of the tile at (x, y, zoom) showing the grid using the 
    *         given color scale
    */
  def visualizeGrid(
    grid  : GridLocation => Temperature,
    colors: Iterable[(Temperature, Color)],
    tile  : Tile
  ): ImmutableImage =                                                    // TODO

    val (width, height) = (256, 256)
    val alpha: Int = 127

    val pixels: IndexedSeq[(Int, Int)] =
      for
        x <- 0 until height
        y <- 0 until width
      yield (x, y)

    // Once again we have to keep passing down x,y b/c of stupid Pixel method.
    val locations: ParSeq[(Int, Int, Location)] =
      pixels
        .par
        .map { case (x, y) =>
          (x, y, Tile(y + tile.y * width, x + tile.x * height, tile.zoom + 8)) }
        .map { case (x, y, tile) => (x, y, tileLocation(tile)) }

    val predictedTemps: ParSeq[(Int, Int, Temperature)] =
      locations map { case (x, y, Location(lat, lon)) =>
        val x0: Int = floor(lon).toInt
        val x1: Int =  ceil(lon).toInt
        val y0: Int =  ceil(lat).toInt
        val y1: Int = floor(lat).toInt
        val d00: Temperature = grid(GridLocation(y0, x0))
        val d01: Temperature = grid(GridLocation(y1, x0))
        val d10: Temperature = grid(GridLocation(y0, x1))
        val d11: Temperature = grid(GridLocation(y1, x1))
        (x, y, bilinearInterpolation(CellPoint(lon - x0, y0 - lat),
                                     d00, d01, d10, d11))
      }

    val coloredLocations: ParSeq[(Int, Int, Color)] =
      predictedTemps map {
        case (x, y, temp) => (x, y, interpolateColor(colors, temp)) }

    val pixelArray: Array[Pixel] =
      coloredLocations
        .map { case (x, y, color) =>
          Pixel(x, y, color.red, color.green, color.blue, alpha) }
        .toArray

    ImmutableImage.wrapPixels(width, height, pixelArray, ImageMetadata.empty)
