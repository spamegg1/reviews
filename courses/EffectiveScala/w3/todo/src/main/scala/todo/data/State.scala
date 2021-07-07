package todo
package data

import java.time.ZonedDateTime

/**
 * The State enumeration represents the different states that a task can be in.
 *
 * You should NOT modify this file.
 */
enum State:
  /**
   * True if this task is completed, false otherwise
   */ 
  def completed: Boolean  =
    this match {
      case _: State.Completed => true
      case _ => false
    }
  /**
   * True if this task is active, false otherwise
   */ 
  def active: Boolean  =
    this match {
      case State.Active => true
      case _ => false
    }

  case Active
  case Completed(data: ZonedDateTime)

object State:
  /**
   * Create a State that is completed at the current time
   */
  def completedNow: State =
    Completed(ZonedDateTime.now())
