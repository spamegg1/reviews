package scalashop

import java.awt.*
import java.awt.event.*
import java.awt.image.*
import java.io.*
import javax.imageio.*
import javax.swing.*
import javax.swing.event.*

class PhotoCanvas extends JComponent:

  var imagePath: Option[String] = None

  var image = loadScalaImage()

  override def getPreferredSize =
    Dimension(image.width, image.height)

  private def loadScalaImage(): Img =
    val stream = this.getClass.getResourceAsStream("/scalashop/scala.jpg")
    try
      loadImage(stream)
    finally
      stream.close()

  private def loadFileImage(path: String): Img =
    val stream = FileInputStream(path)
    try
      loadImage(stream)
    finally
      stream.close()

  private def loadImage(inputStream: InputStream): Img =
    val bufferedImage = ImageIO.read(inputStream)
    val width = bufferedImage.getWidth
    val height = bufferedImage.getHeight
    val img = Img(width, height)
    for x <- 0 until width; y <- 0 until height do img(x, y) = bufferedImage.getRGB(x, y)
    img

  def reload(): Unit =
    image = imagePath match
      case Some(path) => loadFileImage(path)
      case None => loadScalaImage()
    repaint()

  def loadFile(path: String): Unit =
    imagePath = Some(path)
    reload()

  def applyFilter(filterName: String, numTasks: Int, radius: Int): Unit =
    val dst = Img(image.width, image.height)
    filterName match
      case "horizontal-box-blur" =>
        HorizontalBoxBlur.parBlur(image, dst, numTasks, radius)
      case "vertical-box-blur" =>
        VerticalBoxBlur.parBlur(image, dst, numTasks, radius)
      case "" =>
    image = dst
    repaint()

  override def paintComponent(gcan: Graphics) =
    super.paintComponent(gcan)

    val width = image.width
    val height = image.height
    val bufferedImage = BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB)
    for x <- 0 until width; y <- 0 until height do bufferedImage.setRGB(x, y, image(x, y))

    gcan.drawImage(bufferedImage, 0, 0, null)

