object CaseObjectTask:
  def findDeltaCoord(bot: Bot, coordinates: Coordinates): Int =
    bot match
      case TurtleBot => 1
      case HareBot => 3
      case _ => 0

  def move(bot: Bot, direction: Direction, coordinates: Coordinates): Coordinates =
    val delta = findDeltaCoord(bot, coordinates)
    val newCoordinates = direction mat/* Compute the new coordinates */    newCoordinates
