
given obviousInt: Int = 42

given String = "An obvious String"
//given anotherString as String = "An obvious String"

summon[Int]
summon[String]

given Ordering[Double] with
  def compare(x: Double, y: Double): Int =
    if x < y then -1 else if x > y then 1 else 0

summon[Ordering[Double]]