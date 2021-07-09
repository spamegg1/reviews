
case class Rational(num: Int, denom: Int)

given Ordering[Rational] with
  def compare(r1: Rational, r2: Rational): Int =
    r1.num * r2.denom - r2.num * r1.denom

val rationals = List(Rational(6, 2), Rational(7,2), Rational(5,2), Rational(5, 3))

summon[Ordering[Rational]].lt(Rational(6, 2), Rational(7,2))

extension [A: Ordering](lhs: A)
  def < (rhs: A) = summon[Ordering[A]].lt(lhs, rhs)
  def <= (rhs: A) = summon[Ordering[A]].lteq(lhs, rhs)
  def > (rhs: A) = summon[Ordering[A]].gt(lhs, rhs)
  def >= (rhs: A) = summon[Ordering[A]].gteq(lhs, rhs)
end extension

Rational(6, 2) < Rational(7,2)
Rational(6, 2) <= Rational(7,2)

Rational(6, 2) < Rational(3, 1)
Rational(6, 2) <= Rational(3, 1)

Rational(6, 2) > Rational(7,2)
Rational(6, 2) >= Rational(7,2)

Rational(6, 2) > Rational(3, 1)
Rational(6, 2) >= Rational(3, 1)