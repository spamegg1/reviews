package ch.epfl.lamp

import sbt._
import sbt.Keys._

/**
  * Settings shared by all assignments, reused in various tasks.
  */
object MOOCSettings extends AutoPlugin {

  object autoImport {
    val course = SettingKey[String]("course")
    val assignment = SettingKey[String]("assignment")
    val datasetUrl = settingKey[String]("URL of the dataset used for testing")
    val downloadDataset = taskKey[File]("Download the dataset required for the assignment")
    val assignmentVersion = settingKey[String]("Hash string indicating the version of the assignment")
  }

  import autoImport._

  lazy val downloadDatasetDef = downloadDataset := {
    val logger = streams.value.log

    datasetUrl.?.value match {
      case Some(url) => 

        import scalaj.http.Http
        import sbt.io.IO
        val dest = (Compile / resourceManaged).value / assignment.value / url.split("/").last
        if (!dest.exists()) {
          IO.touch(dest)
          logger.info(s"Downloading $url")
          val res = Http(url).method("GET")
          val is = res.asBytes.body
          IO.write(dest, is)
        }
        dest
      case None => 
        logger.info(s"No dataset defined in datasetUrl")
        throw new sbt.MessageOnlyException("No dataset to download for this assignment")
    }
  }

  override val projectSettings: Seq[Def.Setting[_]] = Seq(
    downloadDatasetDef,
    Test / parallelExecution := false,
    // Report test result after each test instead of waiting for every test to finish
    Test / logBuffered := false,
    name := s"${course.value}-${assignment.value}"
  )
}
