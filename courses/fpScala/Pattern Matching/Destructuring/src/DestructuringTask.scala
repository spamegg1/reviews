object DestructuringTask:
  case class RGB(alpha: Int, red: Int, green: Int, blue: Int)

  def colorDescription(color: RGB): String =
    /* TODO */