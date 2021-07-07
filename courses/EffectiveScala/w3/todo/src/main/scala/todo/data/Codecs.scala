package todo
package data

import io.circe.*
import io.circe.syntax.*
import java.time.ZonedDateTime

/*
 * The Codecs describe how to convert Scala data into JSON and vice versa.
 *
 * You MUST NOT change these definitions, as the user interface depends on the
 * JSON encoding and changing the definitions here will break the user
 * interface.
 */
object Codecs:
  given idEncoder: Codec[Id] with
    def apply(c: HCursor): Decoder.Result[Id] =
      c.downField("id").as[Int].map(id => Id(id))

    def apply(id: Id): Json =
      Json.obj("id" -> Json.fromInt(id.toInt))


  given stateCodec: Codec[State] with
    def apply(c: HCursor): Decoder.Result[State] =
      c.downField("state").as[String].flatMap {
        case "active" =>
          Right(State.Active)

        case "completed" =>
          c.downField("date")
            .as[String]
            .map(s => ZonedDateTime.parse(s))
            .map(d => State.Completed(d))

        case err =>
          Left(
            DecodingFailure(
              s"The task type ${err} is not one of the expected types of 'active' or 'completed'",
              List.empty
            )
          )
      }

    def apply(s: State): Json =
      s match
        case State.Active =>
          Json.obj("state" -> Json.fromString("active"))
        case State.Completed(date) =>
          Json.obj(
            "state" -> Json.fromString("completed"),
            "date" -> Json.fromString(date.toString)
          )


  given tagCodec: Codec[Tag] with
    def apply(c: HCursor): Decoder.Result[Tag] =
      c.downField("tag").as[String].map(n => Tag(n))

    def apply(t: Tag): Json =
      Json.obj("tag" -> Json.fromString(t.tag))


  given tagsCodec: Codec[Tags] with
    def apply(c: HCursor): Decoder.Result[Tags] =
      c.as(Decoder.decodeList(tagCodec)).map(t => Tags(t))

    def apply(t: Tags): Json =
      Json.arr(t.tags.toArray.map(tag => tag.asJson)*)


  given taskCodec: Codec[Task] with
    def apply(c: HCursor): Decoder.Result[Task] =
      for {
        state <- c.downField("state").as[State]
        description <- c.downField("description").as[String]
        notes <- c.downField("notes").as[Option[String]]
        tags <- c.downField("tags").as[List[Tag]]
      } yield Task(state, description, notes, tags)

    def apply(t: Task): Json =
      Json.obj(
        "state" -> t.state.asJson,
        "description" -> Json.fromString(t.description),
        "notes" -> t.notes.asJson,
        "tags" -> t.tags.asJson
      )


  given tasksCodec: Codec[Tasks] with
    val elementDecoder = new Decoder[(Id, Task)]:
      def apply(c: HCursor): Decoder.Result[(Id, Task)] =
        for {
          id <- c.downField("id").as[Int]
          task <- c.downField("task").as[Task]
        } yield (Id(id) -> task)

    def apply(c: HCursor): Decoder.Result[Tasks] =
      c.as(Decoder.decodeList(elementDecoder)).map(t => Tasks(t))

    def apply(t: Tasks): Json =
      Json.arr(
        t.tasks.toArray.map {
          case (id, task) =>
            Json.obj("id" -> Json.fromInt(id.toInt), "task" -> task.asJson)
        }*
      )
