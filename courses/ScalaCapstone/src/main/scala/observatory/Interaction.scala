package observatory

import com.sksamuel.scrimage.{Image, Pixel}
import observatory.Visualization.{interpolateColor, predictTemperature}

import scala.math._

/**
  * 3rd milestone: interactive visualization
  *
  * In Web based mapping applications, the whole map is broken down into
  * small images of size 256×256 pixels, called tiles.
  * Each tile shows a part of the map at a given location and zoom level.
  * Your work consists in producing these tiles using Web Mercator projection:
  *     https://en.wikipedia.org/wiki/Web_Mercator_projection
  *
  *
  * ********************
  * Tile generation
  * ********************
  * To describe the position and size of tiles,
  * we need to introduce the tile coordinate system:
  *     https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames#X_and_Y
  * which is composed of an x value, a y value and a zoom level.
  *
  * Coordinates in this system are represented by the Tile case class,
  * defined in models.scala:
  *     case class Tile(x: Int, y: Int, zoom: Int)
  *
  * The tile method converts a tile's geographic position to its corresponding
  * GPS coordinates, by applying the Web Mercator projection:
  *     def tile(temperatures: Iterable[(Location, Temperature)],
  *              colors: Iterable[(Temperature, Color)],
  *              tile: Tile): Image
  *
  * This method returns a 256×256 image showing the given temperatures, using
  * the given color scale, at the location corresponding to the given zoom,
  * x and y values.
  * Note that the pixels of the image must be a little bit transparent so that
  * when we will overlay the tile on the map, the map will still be visible.
  * We recommend using an alpha value of 127.
  *
  * Hint: you will have to compute the corresponding latitude and longitude of
  * each pixel within a tile.
  * A simple way to achieve that is to rely on the fact that each pixel in a
  * tile can be thought of a subtile at a higher zoom level (256 = 2⁸):
  *     https://wiki.openstreetmap.org/wiki/Slippy_map_tilenames#Subtiles
  *
  *
  * ***********************************
  * Integration with a Web application
  * ***********************************
  * Once you are able to generate tiles, you can embed them in a Web page.
  * To achieve this you first have to generate all the tiles for zoom levels
  * going from 0 to 3. (Actually you don’t have to generate all the tiles,
  * since this operation consumes a lot of CPU.
  * You can choose to generate tiles for just one zoom level, e.g. 2).
  * To each zoom level corresponds tiles partitioning the space.
  * For instance, for the zoom level “0” there is only one tile,
  * whose (x, y) coordinates are (0, 0).
  * For the zoom level “1”, there are four tiles, whose coordinates are
  *     (0, 0) (top-left),
  *     (0, 1) (bottom-left),
  *     (1, 0) (top-right) and
  *     (1, 1) (bottom-right).
  *
  * The interaction.html file contains a minimalist Web application displaying
  * a map and a temperature overlay.
  * In order to integrate your tiles with the application,
  * you must generate them in files located according to the following scheme:
  *     target/temperatures/2015/<zoom>/<x>-<y>.png.
  * Where <zoom> is replaced by the zoom level, and <x> and <y> are
  * replaced by the tile coordinates.
  * For instance, the tile located at coordinates (0, 1),
  * for the zoom level 1 will have to be located in the following file:
  *     target/temperatures/2015/1/0-1.png.
  *
  * Once you have generated the files you want to visualize,
  * just open the interaction.html file in a Web browser.
  *
  *
  * *******************************************************
  * Future integration with a more complete Web application
  * *******************************************************
  * At the end of the project you will be going to display these temperature
  * data in a more complete Web application,
  * allowing for example to select which year to visualize.
  * You can prepare this integration by generating the tiles for
  * all the years between 1975 and 2015.
  * You should put the generated images in the following location:
  *     target/temperatures/<year>/<zoom>/<x>-<y>.png.
  *
  * This is going to take a lot of time, but you can make the process faster:
  *     Identify which parts of the process are independent 
  *         and perform them in parallel ;
  *     Reduce the quality of the tiles.
  *         For instance, instead of computing 256×256 images, compute 128×128
  *         images (that’s going to be 4 times fewer pixels to compute)
  *         and then scale them to fit the expected tile size.
  *
  * Finally, you will have to implement the following method:
  *     def generateTiles[Data](yearlyData: Iterable[(Year, Data)],
  *                             generateImage: (Year, Tile, Data) => Unit
  *                            ): Unit
  *
  * This method generates all the tiles for a given dataset yearlyData,
  * for zoom levels 0 to 3 (included).
  * The dataset contains pairs of (Year, Data) values, or, said otherwise,
  * data associated with years.
  * In your case, this data will be the result of
  *     Extraction.locationYearlyAverageRecords.
  * The second parameter of the generateTiles method is a function that takes
  *     a year,
  *     the coordinates of the tile to generate, and
  *     the data associated with the year,
  * and computes the tile and writes it on your filesystem.
  *
  */
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
