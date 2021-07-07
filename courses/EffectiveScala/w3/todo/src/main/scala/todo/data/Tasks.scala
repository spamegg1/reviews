package todo
package data


/*
 * Tasks represents a collection of Task items. We assume order is important.
 *
 * You should NOT modify this file.
 */
final case class Tasks(tasks: Iterable[(Id, Task)]):
  def toList: List[(Id, Task)] =
    tasks.toList

  def toMap: Map[Id, Task] =
    tasks.toMap

object Tasks:
  val empty: Tasks = Tasks(List.empty)
