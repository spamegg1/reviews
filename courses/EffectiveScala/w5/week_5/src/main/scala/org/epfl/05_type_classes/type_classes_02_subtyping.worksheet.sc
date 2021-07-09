// Let's first explore using subtyping

trait Comparable:
  def compareTo(that: Comparable): Int

case class Rational(num: Int, denom: Int) extends Comparable:
  def compareTo(that: Comparable): Int = that match
    case other: Rational =>
      num * other.denom - other.num * denom
    case _ =>
      throw new IllegalArgumentException
  
Rational(5, 3).compareTo(Rational(2, 1))
Rational(6, 3).compareTo(Rational(2, 1))
Rational(7, 3).compareTo(Rational(2, 1))
