package observatory

import org.junit.Test

trait VisualizationTest extends MilestoneSuite {
  private val milestoneTest = namedMilestoneTest("raw data display", 2) _

  // Implement tests for the methods of the `Visualization` object
//  @Test def `predictTemperature`(): Unit = {
//    assert(true)
//  }

  // Implement tests for the methods of the `Visualization` object
  @Test def `interpolateColorTest`(): Unit = {
    val points: Iterable[(Temperature, Color)] = Iterable(
        (60, Color(255, 255, 255)),
        (32, Color(255, 0, 0)),
        (12, Color(255, 255, 0)),
        (0, Color(0, 255, 255)),
        (-15, Color(0, 0, 255)),
        (-27, Color(255, 0, 255)),
        (-50, Color(33, 0, 107)),
        (-60, Color(0, 0, 0))
      )
    val values: Iterable[Temperature] =
      Iterable(70, 60, 46, 32, 22, 12, 6, 0, -8, -15, -21, -27, -38, -50, -55, -60, -70)
    val colors: Iterable[Color] = values.map(value => Visualization.interpolateColor(points, value))
    val answers: Iterable[Color] = Iterable(
      Color(255, 255, 255), // 70
      Color(255, 255, 255), // 60
      Color(255, 128, 128), // 46
      Color(255, 0, 0), // 32
      Color(255, 128, 0), // 22
      Color(255, 255, 0), // 12
      Color(128, 255, 128), // 6
      Color(0, 255, 255), // 0
      Color(0, 119, 255), // -8
      Color(0, 0, 255), // -15
      Color(128, 0, 255), // -21
      Color(255, 0, 255), // -27
      Color(149, 0, 184), // -38
      Color(33, 0, 107), // -50
      Color(17, 0, 54), // -55
      Color(0, 0, 0), // -60
      Color(0, 0, 0) // -70
    )
    assert(colors == answers)
  }

  @Test def `interpolateColorTest2`(): Unit = {
    val palette: Iterable[(Temperature, Color)] = List(
      (100.0, Color(255, 255, 255)),
      (50.0, Color(0, 0, 0)),
      (0.0, Color(255, 0, 128))
    )
    val values: Iterable[Temperature] = Iterable(50, 0, -10, 200, 75, 25)
    val colors: Iterable[Color] = values.map(value =>
      Visualization.interpolateColor(palette, value))
    val answers: Iterable[Color] = Iterable(
      Color(0, 0, 0),
      Color(255, 0, 128),
      Color(255, 0, 128),
      Color(255, 255, 255),
      Color(128, 128, 128),
      Color(128, 0, 64)
    )
    assert (colors == answers)
  }

  @Test def `predictTemperatureTest`(): Unit = {
    val temps: Iterable[(Location, Temperature)] = Iterable(
      (Location(45.0, -90.0), 10.0),
      (Location(-45.0, 0.0), 20.0)
    )
    val temps2: Iterable[(Location, Temperature)] = Iterable(
      (Location(0.0, 0.0), 10.0)
    )

    val location: Location = Location(0.0, -45.0)
    val location2: Location = Location(0.0, 0.0)

    /* these are calculated with p = 2 */
    assert(Visualization.predictTemperature(temps, location) == 15.0)
    assert(Visualization.predictTemperature(temps2, location2) == 10.0)
    assert(Visualization.predictTemperature(temps, location2) == 18.0)
  }

}
