package observatory

import java.time.LocalDate

trait ExtractionTest extends MilestoneSuite:
  private val milestoneTest = namedMilestoneTest("data extraction", 1) _

  // Implement tests for the methods of the `Extraction` object
  // @Test def `locateTemperatures`(): Unit = {
  //   val path: String = "/home/bora/Desktop/SPEC/C5/observatory/src/main/resources"
  //   Extraction.locateTemperatures(
  //     1975,
  //     path + "/stat.csv",
  //     path + "/temp.csv").foreach(println)
  //   assert(
  //     Extraction.locateTemperatures(
  //       1975,
  //       path + "/stat.csv",
  //       path + "/temp.csv") ==
  //     Iterable(
  //       (LocalDate.of(1975, 12, 6),
  //         Location(37.358,-78.438),
  //         0.0: Temperature),
  //       (LocalDate.of(1975, 1, 29),
  //         Location(37.358,-78.438),
  //         2.000000000000001: Temperature)
  //     )
  //   )
  // }
