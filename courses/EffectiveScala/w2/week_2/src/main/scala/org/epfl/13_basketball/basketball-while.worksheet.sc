// https://scastie.scala-lang.org/tIMNKtPSSBu1IerQFAExoQ

case class Position(x: Double, y: Double):
  def distanceTo(that: Position): Double =
    val dx = this.x - that.x
    val dy = this.y - that.y
    math.sqrt(dx * dx + dy * dy)

  def distanceToLine(line: (Position, Position)): Double =
    val (p1, p2) = line
    math.abs((p2.y - p1.y) * x - (p2.x - p1.x) * y + p2.x * p1.y - p2.y * p1.x) / p1.distanceTo(p2)

object Position:
  val player = Position(0, 1.80)
  val hoop   = Position(6.75, 3.048)

case class Angle(radians: Double)
case class Speed(metersPerSecond: Double)

def isWinningShot(angle: Angle, speed: Speed): Boolean =
  val v0X = speed.metersPerSecond * math.cos(angle.radians)
  val v0Y = speed.metersPerSecond * math.sin(angle.radians)
  val p0X = Position.player.x
  val p0Y = Position.player.y
  val g   = -9.81
  def goesThroughHoop(line: (Position, Position)): Boolean =
    Position.hoop.distanceToLine(line) < 0.01
  def isNotTooFar(position: Position): Boolean =
    position.y > 0 && position.x <= Position.hoop.x + 0.01
  def position(t: Int): Position =
    val x = p0X + v0X * t
    val y = p0Y + v0Y * t + 0.5 * g * t * t
    Position(x, y)

  var previousPosition = position(0)
  var isWinning = false
  var time = 1
  while isNotTooFar(previousPosition) && !isWinning do
    val nextPosition = position(time)
    val line = (previousPosition, nextPosition)
    isWinning = goesThroughHoop(line)
    time = time + 1
    previousPosition = nextPosition
  isWinning
end isWinningShot

val angle = Angle(1.4862)
val speed = Speed(20)

isWinningShot(angle, speed)
