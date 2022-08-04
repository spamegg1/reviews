package funsets

/**
 * 2. Purely Functional Sets.
 */
trait FunSets extends FunSetsInterface:
  /**
   * We represent a set by its characteristic function,
   * i.e. its `contains` predicate.
   */
  override type FunSet = Int => Boolean

  /**
   * Indicates whether a set contains a given element.
   */
  def contains(s: FunSet, elem: Int): Boolean = s(elem)

  /**
   * Returns the set of the one given element.
   */
  def singletonSet(elem: Int): FunSet = _ == elem                        // TODO


  /**
   * Returns the union of the two given sets,
   * the sets of all elements that are in either `s` or `t`.
   */
  def union(s: FunSet, t: FunSet): FunSet = n => s(n) || t(n)            // TODO

  /**
   * Returns the intersection of the two given sets,
   * the set of all elements that are both in `s` and `t`.
   */
  def intersect(s: FunSet, t: FunSet): FunSet = n => s(n) && t(n)        // TODO

  /**
   * Returns the difference of the two given sets,
   * the set of all elements of `s` that are not in `t`.
   */
  def diff(s: FunSet, t: FunSet): FunSet = n => s(n) && !t(n)            // TODO

  /**
   * Returns the subset of `s` for which `p` holds.
   */
  def filter(s: FunSet, p: Int => Boolean): FunSet = n => s(n) && p(n)   // TODO

  /**
   * The bounds for `forall` and `exists` are +/- 1000.
   */
  val bound = 1000

  /**
   * Returns the size of the given funset.
   */
  def funSize(s: FunSet): Int = (-bound to bound).filter(s).size         // TODO

  /**
   * Returns whether all bounded integers within `s` satisfy `p`.
   */
  def forall(s: FunSet, p: Int => Boolean): Boolean =                    // TODO
    def iter(a: Int): Boolean =
      if s(a) && !p(a) then
        false
      else if a > bound then
        true
      else
        iter(a + 1)
    iter(-bound)

  /**
   * Returns true if given funsets contain exactly the same elements.
   */
  def eql(s: FunSet, t: FunSet): Boolean = forall(s, t) && forall(t, s)  // TODO

  /**
   * Returns whether there exists a bounded integer within `s`
   * that satisfies `p`.
   */
  def exists(s: FunSet, p: Int => Boolean): Boolean = !forall(s, !p(_))  // TODO

  /**
   * Returns a set transformed by applying `f` to each element of `s`.
   */
  def map(s: FunSet, f: Int => Int): FunSet =                            // TODO
    n => exists(s, y => n == f(y))

  /**
   * Displays the contents of a set
   */
  def toString(s: FunSet): String =
    val xs =
      for
        i <- -bound to bound
        if contains(s, i)
      yield i
    xs.mkString("{", ",", "}")

  /**
   * Prints the contents of a set on the console.
   */
  def printSet(s: FunSet): Unit = println(toString(s))

object FunSets extends FunSets
