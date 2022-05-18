package ch.epfl.lamp

import sbt._
import Keys._
import scalafix.sbt.ScalafixPlugin.autoImport._

import scalaj.http._
import java.io.{File, FileInputStream, IOException}
import org.apache.commons.codec.binary.Base64
import play.api.libs.json.{Json, JsObject, JsPath}
import scala.util.{Failure, Success, Try}

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
  import MOOCSettings.autoImport._

  // Run scalafix linting after compilation to avoid seeing parser errors twice
  // Keep in sync with the use of scalafix in Grader
  // (--exclude doesn't work (https://github.com/lampepfl-courses/moocs/pull/28#issuecomment-427894795)
  //  so we customize unmanagedSources below instead)
  private val scalafixLinting = Def.taskDyn {
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
    submitSetting,

    fork := true,
    run / connectInput := true,
    outputStrategy := Some(StdoutOutput),
    scalafixConfig := {
      val scalafixDotConf = (baseDirectory.value / ".scalafix.conf")
      if (scalafixDotConf.exists) Some(scalafixDotConf) else None
    }
  ) ++ packageSubmissionZipSettings

  /** **********************************************************
    * SUBMITTING A SOLUTION TO COURSERA
    */

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

  /** Check that the jar exists, isn't empty, isn't crazy big, and can be read
    * If so, encode jar as base64 so we can send it to Coursera
    */
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

  /** Task to submit a solution to coursera */
  val submit = inputKey[Unit]("submit solution to Coursera")
  lazy val submitSetting = submit := {
    // Fail if scalafix linting does not pass.
    scalafixLinting.value

    val args: Seq[String] = Def.spaceDelimited("<arg>").parsed
    val s: TaskStreams = streams.value // for logging
    val jar = (Compile / packageSubmissionZip).value

    val assignmentDetails =
      courseraId.?.value.getOrElse(throw new MessageOnlyException("This assignment can not be submitted to Coursera because the `courseraId` setting is undefined"))
    val assignmentKey = assignmentDetails.key
    val courseName =
      course.value match {
        case "progfun1" => "scala-functional-programming"
        case "progfun2" => "scala-functional-program-design"
        case "parprog1" => "scala-parallel-programming"
        case "bigdata"  => "scala-spark-big-data"
        case "capstone" => "scala-capstone"
        case "reactive" => "scala-akka-reactive"
        case other      => other
      }

    val partId = assignmentDetails.partId
    val itemId = assignmentDetails.itemId
    val premiumItemId = assignmentDetails.premiumItemId

    val (email, secret) = args match {
      case email :: secret :: Nil =>
        (email, secret)
      case _ =>
        val inputErr =
          s"""|Invalid input to `submit`. The required syntax for `submit` is:
              |submit <email-address> <submit-token>
              |
              |The submit token is NOT YOUR LOGIN PASSWORD.
              |It can be obtained from the assignment page:
              |https://www.coursera.org/learn/$courseName/programming/$itemId
              |${
                premiumItemId.fold("") { id =>
                  s"""or (for premium learners):
                     |https://www.coursera.org/learn/$courseName/programming/$id
                   """.stripMargin
                }
              }
          """.stripMargin
        s.log.error(inputErr)
        failSubmit()
    }

    val base64Jar = prepareJar(jar, s)
    val json =
      s"""|{
          |   "assignmentKey":"$assignmentKey",
          |   "submitterEmail":"$email",
          |   "secret":"$secret",
          |   "parts":{
          |      "$partId":{
          |         "output":"$base64Jar"
          |      }
          |   }
          |}""".stripMargin

    def postSubmission[T](data: String): Try[HttpResponse[String]] = {
      val http = Http("https://www.coursera.org/api/onDemandProgrammingScriptSubmissions.v1")
      val hs = List(
        ("Cache-Control", "no-cache"),
        ("Content-Type", "application/json")
      )
      s.log.info("Connecting to Coursera...")
      val response = Try(http.postData(data)
                         .headers(hs)
                         .option(HttpOptions.connTimeout(10000)) // scalaj default timeout is only 100ms, changing that to 10s
                         .asString) // kick off HTTP POST
      response
    }

    val connectMsg =
      s"""|Attempting to submit "${assignment.value}" assignment in "$courseName" course
          |Using:
          |- email: $email
          |- submit token: $secret""".stripMargin
    s.log.info(connectMsg)

    def reportCourseraResponse(response: HttpResponse[String]): Unit = {
      val code = response.code
      val respBody = response.body

       /* Sample JSON response from Coursera
      {
        "message": "Invalid email or token.",
        "details": {
          "learnerMessage": "Invalid email or token."
        }
      }
      */

      // Success, Coursera responds with 2xx HTTP status code
      if (response.is2xx) {
        val successfulSubmitMsg =
          s"""|Successfully connected to Coursera. (Status $code)
              |
                |Assignment submitted successfully!
              |
                |You can see how you scored by going to:
              |https://www.coursera.org/learn/$courseName/programming/$itemId/
              |${
            premiumItemId.fold("") { id =>
              s"""or (for premium learners):
                 |https://www.coursera.org/learn/$courseName/programming/$id
                       """.stripMargin
            }
          }
              |and clicking on "My Submission".""".stripMargin
        s.log.info(successfulSubmitMsg)
      }

      // Failure, Coursera responds with 4xx HTTP status code (client-side failure)
      else if (response.is4xx) {
        val result = Try(Json.parse(respBody)).toOption
        val learnerMsg = result match {
          case Some(resp: JsObject) =>
            (JsPath \ "details" \ "learnerMessage").read[String].reads(resp).get
          case Some(x) => // shouldn't happen
            "Could not parse Coursera's response:\n" + x
          case None =>
            "Could not parse Coursera's response:\n" + respBody
        }
        val failedSubmitMsg =
          s"""|Submission failed.
              |There was something wrong while attempting to submit.
              |Coursera says:
              |$learnerMsg (Status $code)""".stripMargin
        s.log.error(failedSubmitMsg)
      }

      // Failure, Coursera responds with 5xx HTTP status code (server-side failure)
      else if (response.is5xx) {
        val failedSubmitMsg =
          s"""|Submission failed.
              |Coursera seems to be unavailable at the moment (Status $code)
              |Check https://status.coursera.org/ and try again in a few minutes.
           """.stripMargin
        s.log.error(failedSubmitMsg)
      }

      // Failure, Coursera repsonds with an unexpected status code
      else {
        val failedSubmitMsg =
          s"""|Submission failed.
              |Coursera replied with an unexpected code (Status $code)
           """.stripMargin
        s.log.error(failedSubmitMsg)
      }
    }

    // kick it all off, actually make request
    postSubmission(json) match {
      case Success(resp) => reportCourseraResponse(resp)
      case Failure(e) =>
        val failedConnectMsg =
          s"""|Connection to Coursera failed.
              |There was something wrong while attempting to connect to Coursera.
              |Check your internet connection.
              |${e.toString}""".stripMargin
        s.log.error(failedConnectMsg)
    }

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
