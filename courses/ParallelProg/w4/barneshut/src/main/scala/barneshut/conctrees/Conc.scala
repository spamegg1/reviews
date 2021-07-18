package barneshut
package conctrees

import scala.annotation.tailrec

sealed trait Conc[@specialized(Int, Long, Float, Double) +T]:
  def level: Int
  def size: Int
  def left: Conc[T]
  def right: Conc[T]
  def normalized = this

object Conc:

  case class <>[+T](left: Conc[T], right: Conc[T]) extends Conc[T]:
    val level = 1 + math.max(left.level, right.level)
    val size = left.size + right.size

  sealed trait Leaf[+T] extends Conc[T]:
    def left = sys.error("Leaves do not have children.")
    def right = sys.error("Leaves do not have children.")

  case object Empty extends Leaf[Nothing]:
    def level = 0
    def size = 0

  class Single[@specialized(Int, Long, Float, Double) +T](val x: T) extends Leaf[T]:
    def level = 0
    def size = 1
    override def toString = s"Single($x)"

  class Chunk[@specialized(Int, Long, Float, Double) T](val array: Array[T], val size: Int, val k: Int)
  extends Leaf[T]:
    def level = 0
    override def toString = s"Chunk(${array.mkString("", ", ", "")}; $size; $k)"

  case class Append[+T](left: Conc[T], right: Conc[T]) extends Conc[T]:
    val level = 1 + math.max(left.level, right.level)
    val size = left.size + right.size
    override def normalized =
      def wrap[T](xs: Conc[T], ys: Conc[T]): Conc[T] = (xs: @unchecked) match
        case Append(ws, zs) => wrap(ws, zs <> ys)
        case xs => xs <> ys
      wrap(left, right)

  def concatTop[T](xs: Conc[T], ys: Conc[T]) =
    if xs == Empty then ys
    else if ys == Empty then xs
    else concat(xs, ys)

  private def concat[T](xs: Conc[T], ys: Conc[T]): Conc[T] =
    val diff = ys.level - xs.level
    if diff >= -1 && diff <= 1 then <>(xs, ys)
    else if diff < -1 then
      if xs.left.level >= xs.right.level then
        val nr = concat(xs.right, ys)
        <>(xs.left, nr)
      else
        val nrr = concat(xs.right.right, ys)
        if nrr.level == xs.level - 3 then
          val nl = xs.left
          val nr = <>(xs.right.left, nrr)
          <>(nl, nr)
        else
          val nl = <>(xs.left, xs.right.left)
          val nr = nrr
          <>(nl, nr)
    else
      if ys.right.level >= ys.left.level then
        val nl = concat(xs, ys.left)
        <>(nl, ys.right)
      else
        val nll = concat(xs, ys.left.left)
        if nll.level == ys.level - 3 then
          val nl = <>(nll, ys.left.right)
          val nr = ys.right
          <>(nl, nr)
        else
          val nl = nll
          val nr = <>(ys.left.right, ys.right)
          <>(nl, nr)

  def appendTop[T](xs: Conc[T], ys: Leaf[T]): Conc[T] = (xs: @unchecked) match
    case xs: Append[T] => append(xs, ys)
    case _ <> _ => Append(xs, ys)
    case Empty => ys
    case xs: Leaf[T] => <>(xs, ys)
  @tailrec private def append[T](xs: Append[T], ys: Conc[T]): Conc[T] =
    if xs.right.level > ys.level then Append(xs, ys)
    else
      val zs = <>(xs.right, ys)
      xs.left match
        case ws @ Append(_, _) => append(ws, zs)
        case ws if ws.level <= zs.level => ws <> zs
        case ws => Append(ws, zs)

  def traverse[@specialized(Int, Long, Float, Double) T, @specialized(Int, Long, Float, Double) U](xs: Conc[T], f: T => U): Unit = (xs: @unchecked) match
    case left <> right =>
      traverse(left, f)
      traverse(right, f)
    case s: Single[T] =>
      f(s.x)
    case c: Chunk[_ <: T] =>
      val a = c.array
      val sz = c.size
      var i = 0
      while i < sz do
        f(a(i))
        i += 1
    case Empty =>
    case Append(left, right) =>
      traverse(left, f)
      traverse(right, f)
    case _ =>
      sys.error("All cases should have been covered: " + xs + ", " + xs.getClass)

  def iterator[@specialized(Int, Long, Float, Double) T](xs: Conc[T]): Iterator[T] = (xs: @unchecked) match
    case left <> right       => iterator(left) ++ iterator(right)
    case s: Single[T]        => Iterator.single(s.x)
    case c: Chunk[_ <: T]    => c.array.iterator.take(c.size)
    case Empty               => Iterator.empty
    case Append(left, right) => iterator(left) ++ iterator(right)
    case _                   => sys.error("All cases should have been covered: " + xs + ", " + xs.getClass)

