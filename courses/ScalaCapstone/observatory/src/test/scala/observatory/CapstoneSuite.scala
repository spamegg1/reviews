package observatory

import org.scalacheck.Test
import scala.util.Properties.isWin

class CapstoneSuite
  extends ExtractionTest
    with VisualizationTest
    with InteractionTest
    with ManipulationTest
    with Visualization2Test
    with Interaction2Test

trait MilestoneSuite extends munit.FunSuite:

  if (isWin) System.setProperty("hadoop.home.dir", System.getProperty("user.dir") + "\\winutils\\hadoop-3.3.1")

  def namedMilestoneTest(milestoneName: String, level: Int)(block: => Unit): Unit =
    if Grading.milestone >= level then
      block
    else
      fail(s"Milestone $level ($milestoneName) is disabled. To enable it, set the 'Grading.milestone' value to '$level'.")


