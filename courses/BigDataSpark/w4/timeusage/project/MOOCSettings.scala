package ch.epfl.lamp

import sbt._
import sbt.Keys._

/**
 * Coursera uses two versions of each assignment. They both have the same assignment key and part id but have
 * different item ids.
 *
 * @param key Assignment key
 * @param partId Assignment partId
 * @param itemId Item id of the non premium version
 * @param premiumItemId Item id of the premium version (`None` if the assignment is optional)
 */
case class CourseraId(key: String, partId: String, itemId: String, premiumItemId: Option[String])

/**
  * Settings shared by all assignments, reused in various tasks.
  */
object MOOCSettings extends AutoPlugin {

  object autoImport {
    val course = SettingKey[String]("course")
    val assignment = SettingKey[String]("assignment")
    val options = SettingKey[Map[String, Map[String, String]]]("options")
    val courseraId = settingKey[CourseraId]("Coursera-specific information identifying the assignment")
    // Convenient alias
    type CourseraId = ch.epfl.lamp.CourseraId
    val CourseraId = ch.epfl.lamp.CourseraId
  }

  import autoImport._

  override val projectSettings: Seq[Def.Setting[_]] = Seq(
    parallelExecution in Test := false,
    name := s"${course.value}-${assignment.value}"
  )
}
