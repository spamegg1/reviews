object DestructuringTask:
  case class RGB(alpha: Int, red: Int, green: Int, blue: Int)

  def colorDescription(color: RGB): String =
    /* TODO */
    color match
      case RGB(_, 0, 0, 0)       => "White"
      case RGB(_, 255, 0, 0)     => "Red"
      case RGB(_, 0, 255, 0)     => "Green"
      case RGB(_, 0, 0, 255)     => "Blue"
      case RGB(_, 255, 255, 0)   => "Yellow"
      case RGB(_, 0, 255, 255)   => "Cyan"
      case RGB(_, 255, 0, 255)   => "Magenta"
      case RGB(_, 255, 255, 255) => "Black"
      case _                     => color.toString
