object CustomUnapplyTask:
  class RGB(val color: Int)

  object RGB:
    // Implement a custom unapply method to split the color into its
    // four components: alpha, red, green, and blue
    def unapply(rgb: RGB): (Int, Int, Int, Int) = {
/* TODO */
    }

  def printRGB(color: RGB): Unit =
    color match
      case RGB(alpha, red, green, blue) =>
        println(s"alpha: $alpha, red: $red, green: $green, blue: $blue")

  @main
  def main() =
    printRGB(RGB(0xFFFFFFFF))
    printRGB(RGB(0x00FFFFFF))
    printRGB(RGB(0xFF00FFFF))
    printRGB(RGB(0xFFFF00FF))
    printRGB(RGB(0xFFFFFF00))
    printRGB(RGB(0xCA0A3F80))

