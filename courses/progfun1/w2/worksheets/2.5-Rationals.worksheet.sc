class Rational(x: Int, y: Int):
  def numer = x
  def denom = y

  def add(that: Rational) = Rational(
    numer * that.denom + denom * that.numer, denom * that.denom
  )

  def mul(that: Rational) = Rational(
    numer * that.numer, denom * that.denom
  )

  def neg: Rational = Rational(-numer, denom)

  def sub(that: Rational): Rational = add(that.neg)

  override def toString: String = s"$numer / $denom"

end Rational

val x = Rational(1, 3)
val y = Rational(5, 7)
val z = Rational(3, 2)

x.add(y).mul(z)
x.sub(y).sub(z)

