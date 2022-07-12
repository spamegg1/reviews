abstract class Nat:
  def isZero: Boolean
  def pred: Nat
  def succ: Nat
  def +(that: Nat): Nat
  def -(that: Nat): Nat
  def *(that: Nat): Nat
end Nat

object Zero extends Nat:
  def isZero: Boolean = true
  def pred: Nat = ???
  def succ: Nat = Succ(this)
  def +(that: Nat): Nat = that
  def -(that: Nat): Nat = if that.isZero then this else ???
  def *(that: Nat): Nat = this

  override def toString: String = "0"
end Zero

class Succ(n: Nat) extends Nat:
  def isZero: Boolean = false
  def pred: Nat = n
  def succ: Nat = Succ(this)
  def +(that: Nat): Nat = Succ(n + that)
  def -(that: Nat): Nat = if that.isZero then this else n - that.pred
  def *(that: Nat): Nat = n * that + that

  override def toString: String = s"${n.toString.toInt + 1}"
end Succ

val one = Succ(Zero)
val two = Succ(one)
val three = Succ(two)

one + two
two - one
// one - two // error
three * two
two * three
