package todo

import todo.data.*

/**
 * The Model defines an interface for the business
 * logic of the todo list application.
 *
 * You should NOT modify this file.
 */
trait Model:
  /*
   * Create the given Task, returning the Id associated with the new Task.
   */
  def create(task: Task): Id
  /*
   * Get the task associated with the given ID, if one exists.
   */
  def read(id: Id): Option[Task]
  /*
   * Update the task associated with the given ID if one exists. 
   * Returns the updated Task if one exists and was updated,
   * and None otherwise.
   */
  def update(id: Id)(f: Task => Task): Option[Task]
  /*
   * Delete the task associated with the given ID if one exists. 
   * Returns true if the task was deleted, 
   * otherwise false to indicate no task was associated with the ID.
   */
  def delete(id: Id): Boolean
  /*
   * Get a list of all tasks, sorted by ID.
   */
  def tasks: Tasks
  /*
   * Return all the Tasks tagged with the given Tag.
   */
  def tasks(tag: Tag): Tasks
  /*
   * Complete the task with the given ID and
   * return the updated task if one exists.
   */
  def complete(id: Id): Option[Task]
  /*
   * Return all the Tags that are currently in use.
   */
  def tags: Tags
  /*
   * Clear any data in the model. Mostly useful for testing.
   */
  def clear(): Unit
