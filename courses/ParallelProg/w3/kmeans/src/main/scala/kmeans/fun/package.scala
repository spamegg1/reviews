package kmeans.fun


/** The value of every pixel is represented as a 32 bit integer. */
type RGBA = Int

/** Returns the alpha component. */
def alpha(c: RGBA): Double = ((0xff000000 & c) >>> 24).toDouble / 256

/** Returns the red component. */
def red(c: RGBA): Double = ((0x00ff0000 & c) >>> 16).toDouble / 256

/** Returns the green component. */
def green(c: RGBA): Double = ((0x0000ff00 & c) >>> 8).toDouble / 256

/** Returns the blue component. */
def blue(c: RGBA): Double = ((0x000000ff & c) >>> 0).toDouble / 256

/** Used to create an RGBA value from separate components. */
def rgba(r: Double, g: Double, b: Double, a: Double): RGBA =
  (clamp((a * 256).toInt, 0, 255) << 24) |
  (clamp((r * 256).toInt, 0, 255) << 16) |
  (clamp((g * 256).toInt, 0, 255) <<  8) |
  (clamp((b * 256).toInt, 0, 255) <<  0)

/** Restricts the integer into the specified range. */
def clamp(v: Int, min: Int, max: Int): Int =
  if v < min then min
  else if v > max then max
  else v

/** Image is a two-dimensional matrix of pixel values. */
class Img(val width: Int, val height: Int, private val data: Array[RGBA]):
  def this(w: Int, h: Int) = this(w, h, new Array(w * h))
  def apply(x: Int, y: Int): RGBA = data(y * width + x)
  def update(x: Int, y: Int, c: RGBA): Unit = data(y * width + x) = c
