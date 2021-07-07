package todo
package data


/*
 * The Id class encapsulates an integer that represents an Id. This
 * distinguishes Ids from other Ints that may occur in the program.
 *
 * You should NOT modify this file.
 */
final case class Id(toInt: Int):
  def next: Id = this.copy(toInt = toInt + 1)
object Id:
  given idOrdering: Ordering[Id] = Ordering.by(id => id.toInt)
