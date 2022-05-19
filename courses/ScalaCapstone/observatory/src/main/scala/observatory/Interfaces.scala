package observatory

import com.sksamuel.scrimage.ImmutableImage
import scala.collection.parallel.CollectionConverters.given
import java.time.LocalDate

// Interfaces used by the grading infrastructure. Do not change signatures
// or your submission will fail with a NoSuchMethodError.

trait ManipulationInterface:
  def makeGrid(temperatures: Iterable[(Location, Temperature)]): GridLocation => Temperature
  def average(temperaturess: Iterable[Iterable[(Location, Temperature)]]): GridLocation => Temperature
  def deviation(temperatures: Iterable[(Location, Temperature)], normals: GridLocation => Temperature): GridLocation => Temperature

trait Visualization2Interface:
  def bilinearInterpolation(point: CellPoint, d00: Temperature, d01: Temperature, d10: Temperature, d11: Temperature): Temperature
  def visualizeGrid(grid: GridLocation => Temperature, colors: Iterable[(Temperature, Color)], tile: Tile): ImmutableImage

trait VisualizationInterface:
  def predictTemperature(temperatures: Iterable[(Location, Temperature)], location: Location): Temperature
  def interpolateColor(points: Iterable[(Temperature, Color)], value: Temperature): Color
  def visualize(temperatures: Iterable[(Location, Temperature)], colors: Iterable[(Temperature, Color)]): ImmutableImage

trait InteractionInterface:
  def tileLocation(tile: Tile): Location
  def tile(temperatures: Iterable[(Location, Temperature)], colors: Iterable[(Temperature, Color)], tile: Tile): ImmutableImage
  def generateTiles[Data](yearlyData: Iterable[(Year, Data)], generateImage: (Year, Tile, Data) => Unit): Unit

trait ExtractionInterface:
  def locateTemperatures(year: Year, stationsFile: String, temperaturesFile: String): Iterable[(LocalDate, Location, Temperature)]
  def locationYearlyAverageRecords(records: Iterable[(LocalDate, Location, Temperature)]): Iterable[(Location, Temperature)]
