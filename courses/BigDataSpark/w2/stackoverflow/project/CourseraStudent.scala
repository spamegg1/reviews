package ch.epfl.lamp

import sbt._
import Keys._
import scala.util.{Failure, Success, Try}
import scalaj.http._
import play.api.libs.json.{Json, JsObject, JsPath}

/**
 * Coursera uses two versions of each assignment. They both have the same assignment key and part id but have
 * different item ids.
 *
 * @param key Assignment key
 * @param partId Assignment partId
 * @param itemId Item id of the non premium version
 * @param premiumItemId Item id of the premium version (`None` if the assignment is optional)
 */
case class CourseraId(courseId: String, key: String, partId: String, itemId: String, premiumItemId: Option[String])


object CourseraStudent extends AutoPlugin {

  override def requires = super.requires && MOOCSettings && StudentTasks

  override def trigger = allRequirements

  object autoImport {
    val options = SettingKey[Map[String, Map[String, String]]]("options")
    val courseraId = settingKey[CourseraId]("Coursera-specific information identifying the assignment")
    // Convenient alias
    type CourseraId = ch.epfl.lamp.CourseraId
    val CourseraId = ch.epfl.lamp.CourseraId
  } 

  import StudentTasks.autoImport._
  import MOOCSettings.autoImport._
  import autoImport._

  override lazy val projectSettings = Seq(
    submitSetting,
  )

  /** Task to submit a solution to coursera */
  val submit = inputKey[Unit]("submit solution to Coursera")
  lazy val submitSetting = submit := {
    // Fail if scalafix linting does not pass.
    StudentTasks.scalafixLinting.value

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
        StudentTasks.failSubmit()
    }

    val base64Jar = StudentTasks.prepareJar(jar, s)
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

}
