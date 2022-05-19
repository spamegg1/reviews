package ch.epfl.lamp

import sbt._
import Keys._
import scalafix.sbt.ScalafixPlugin.autoImport._

import java.io.{File, FileInputStream, IOException}
import org.apache.commons.codec.binary.Base64

/**
  * Provides tasks for submitting the assignment
  */
object StudentTasks extends AutoPlugin {

  override def requires = super.requires && MOOCSettings

  object autoImport {
    val packageSourcesOnly = TaskKey[File]("packageSourcesOnly", "Package the sources of the project")
    val packageBinWithoutResources = TaskKey[File]("packageBinWithoutResources", "Like packageBin, but without the resources")

    val packageSubmissionZip = TaskKey[File]("packageSubmissionZip")

    val packageSubmission = inputKey[Unit]("package solution as an archive file")
  }

  import autoImport._

  // Run scalafix linting after compilation to avoid seeing parser errors twice
  // Keep in sync with the use of scalafix in Grader
  // (--exclude doesn't work (https://github.com/lampepfl-courses/moocs/pull/28#issuecomment-427894795)
  //  so we customize unmanagedSources below instead)
  val scalafixLinting = Def.taskDyn {
    if (new File(".scalafix.conf").exists()) {
      (Compile / scalafix).toTask(" --check").dependsOn(Compile / compile)
    } else Def.task(())
  }

  override lazy val projectSettings = Seq(
    // Run scalafix linting in parallel with the tests
    (Test / test) := {
      scalafixLinting.value
      (Test / test).value
    },

    packageSubmissionSetting,

    fork := true,
    run / connectInput := true,
    outputStrategy := Some(StdoutOutput),
    scalafixConfig := {
      val scalafixDotConf = (baseDirectory.value / ".scalafix.conf")
      if (scalafixDotConf.exists) Some(scalafixDotConf) else None
    }
  ) ++ packageSubmissionZipSettings

  val packageSubmissionZipSettings = Seq(
    packageSubmissionZip := {
      val submission = crossTarget.value / "submission.zip"
      val sources = (Compile / packageSourcesOnly).value
      val binaries = (Compile / packageBinWithoutResources).value
      IO.zip(Seq(sources -> "sources.zip", binaries -> "binaries.jar"), submission, None)
      submission
    },
    packageSourcesOnly / artifactClassifier := Some("sources"),
    Compile / packageBinWithoutResources / artifact ~= (art => art.withName(art.name + "-without-resources"))
  ) ++
  inConfig(Compile)(
    Defaults.packageTaskSettings(packageSourcesOnly, Defaults.sourceMappings) ++
    Defaults.packageTaskSettings(packageBinWithoutResources, Def.task {
      val relativePaths =
        (Compile / resources).value.flatMap(Path.relativeTo((Compile / resourceDirectories).value)(_))
      (Compile / packageBin / mappings).value.filterNot { case (_, path) => relativePaths.contains(path) }
    })
  )

  val maxSubmitFileSize = {
    val mb = 1024 * 1024
    10 * mb
  }

  def prepareJar(jar: File, s: TaskStreams): String = {
    val errPrefix = "Error submitting assignment jar: "
    val fileLength = jar.length()
    if (!jar.exists()) {
      s.log.error(errPrefix + "jar archive does not exist\n" + jar.getAbsolutePath)
      failSubmit()
    } else if (fileLength == 0L) {
      s.log.error(errPrefix + "jar archive is empty\n" + jar.getAbsolutePath)
      failSubmit()
    } else if (fileLength > maxSubmitFileSize) {
      s.log.error(errPrefix + "jar archive is too big. Allowed size: " +
        maxSubmitFileSize + " bytes, found " + fileLength + " bytes.\n" +
        jar.getAbsolutePath)
      failSubmit()
    } else {
      val bytes = new Array[Byte](fileLength.toInt)
      val sizeRead = try {
        val is = new FileInputStream(jar)
        val read = is.read(bytes)
        is.close()
        read
      } catch {
        case ex: IOException =>
          s.log.error(errPrefix + "failed to read sources jar archive\n" + ex.toString)
          failSubmit()
      }
      if (sizeRead != bytes.length) {
        s.log.error(errPrefix + "failed to read the sources jar archive, size read: " + sizeRead)
        failSubmit()
      } else encodeBase64(bytes)
    }
  }

  /** Task to package solution to a given file path */
  lazy val packageSubmissionSetting = packageSubmission := {
    // Fail if scalafix linting does not pass.
    scalafixLinting.value

    val args: Seq[String] = Def.spaceDelimited("[path]").parsed
    val s: TaskStreams = streams.value // for logging
    val jar = (Compile / packageSubmissionZip).value

    val base64Jar = prepareJar(jar, s)

    val path = args.headOption.getOrElse((baseDirectory.value / "submission.jar").absolutePath)
    scala.tools.nsc.io.File(path).writeAll(base64Jar)
  }

  def failSubmit(): Nothing = {
    sys.error("Submission failed")
  }

  /**
    * *****************
    * DEALING WITH JARS
    */
  def encodeBase64(bytes: Array[Byte]): String =
    new String(Base64.encodeBase64(bytes))
}
