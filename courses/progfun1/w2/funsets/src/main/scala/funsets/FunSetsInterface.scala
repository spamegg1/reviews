package funsets

/**
 * The interface used by the grading infrastructure. You should not edit any
 * code here, or your submission may fail with a NoSuchMethodError.
 */
trait FunSetsInterface:
  type FunSet = Int => Boolean

  def contains(s: FunSet, elem: Int): Boolean
  def singletonSet(elem: Int): FunSet
  def union(s: FunSet, t: FunSet): FunSet
  def intersect(s: FunSet, t: Int => Boolean): FunSet
  def diff(s: FunSet, t: FunSet): FunSet
  def filter(s: FunSet, p: Int => Boolean): FunSet
  def forall(s: FunSet, p: Int => Boolean): Boolean
  def exists(s: FunSet, p: Int => Boolean): Boolean
  def map(s: FunSet, f: Int => Int): FunSet
  def toString(s: FunSet): String
