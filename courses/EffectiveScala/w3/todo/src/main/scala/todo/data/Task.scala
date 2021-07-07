package todo
package data


/*
 * A Task represents a job to be done.
 *
 * You should NOT modify this file.
 */
final case class Task(
  state: State,
  description: String,
  notes: Option[String],
  tags: List[Tag]
):
  def complete: Task =
    val newState =
      state match
        case State.Active => State.completedNow
        case State.Completed(d) => State.Completed(d)
    this.copy(state = newState)
