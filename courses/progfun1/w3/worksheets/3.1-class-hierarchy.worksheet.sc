abstract class IntSet:
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
  def union(that: IntSet): IntSet
end IntSet

object Empty extends IntSet:
  def incl(x: Int): IntSet = NonEmpty(x, Empty, Empty)
  def contains(x: Int): Boolean = false
  def union(that: IntSet): IntSet = that

  override def toString: String = "Empty"

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet:
  def incl(x: Int): IntSet =
    if x < elem then NonEmpty(elem, left incl x, right) else
    if x > elem then NonEmpty(elem, left, right incl x)
    else this
  def contains(x: Int): Boolean =
    if x < elem then left.contains(x) else
    if x > elem then right.contains(x)
    else true
  def union(that: IntSet): IntSet = (left union (right union that)) incl elem

  override def toString: String = s"NonEmpty($elem, $left, $right)"

val x = NonEmpty(1, Empty, Empty)
val y = NonEmpty(3, Empty, Empty)
val z = NonEmpty(2, x, y)

val s = NonEmpty(4, Empty, Empty)
val t = NonEmpty(6, Empty, Empty)
val u = NonEmpty(5, s, t)

z union u
u union z
