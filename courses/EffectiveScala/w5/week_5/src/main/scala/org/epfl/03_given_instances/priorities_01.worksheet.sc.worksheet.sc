trait A:
  given x: Int = 0

trait B extends A:
  given y: Int = 1

object C extends B:
  val n = summon[Int]

C.n

given x: Int = 0
def foo() =
  given y: Int = 1
  summon[Int]

foo()

class General()
class Specific() extends General()

given general: General = General()
given specific: Specific = Specific()

summon[General]
