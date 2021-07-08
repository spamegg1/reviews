
object Generator:
  var previous: Int = 42

  def nextInt(): Int =
    val result = previous * 22_695_477 + 1
    previous = result
    result
  
  def between(x: Int, y: Int): Int =
    val min = math.min(x, y)
    val delta = math.abs(x - y)
    val randomValue = nextInt()
    scala.math.abs(randomValue % delta) + min
end Generator

val x = Generator.nextInt()
val y = Generator.nextInt()

val r = Generator.between(-100, 100)
val s = Generator.between(-100, 100)

val windowSide = Generator.between(1, 4)
val windowArea = windowSide * windowSide