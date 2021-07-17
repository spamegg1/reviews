package scalashop

// Interfaces used by the grading infrastructure. Do not change signatures
// or your submission will fail with a NoSuchMethodError.

trait HorizontalBoxBlurInterface:
  def blur(src: Img, dst: Img, from: Int, end: Int, radius: Int): Unit
  def parBlur(src: Img, dst: Img, numTasks: Int, radius: Int): Unit

trait VerticalBoxBlurInterface:
  def blur(src: Img, dst: Img, from: Int, end: Int, radius: Int): Unit
  def parBlur(src: Img, dst: Img, numTasks: Int, radius: Int): Unit
