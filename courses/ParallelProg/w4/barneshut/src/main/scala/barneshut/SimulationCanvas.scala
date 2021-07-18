package barneshut

import java.awt.*
import java.awt.event.*
import javax.swing.*
import javax.swing.event.*

class SimulationCanvas(val model: SimulationModel) extends JComponent:

  val MAX_RES = 3000

  val pixels = Array[Int](MAX_RES * MAX_RES)

  override def paintComponent(gcan: Graphics) =
    super.paintComponent(gcan)

    val width = getWidth
    val height = getHeight
    val img = image.BufferedImage(width, height, image.BufferedImage.TYPE_INT_ARGB)

    // clear canvas pixels
    for x <- 0 until MAX_RES; y <- 0 until MAX_RES do pixels(y * width + x) = 0

    // count number of bodies in each pixel
    for b <- model.bodies do
      val px = ((b.x - model.screen.minX) / model.screen.width * width).toInt
      val py = ((b.y - model.screen.minY) / model.screen.height * height).toInt
      if px >= 0 && px < width && py >= 0 && py < height then pixels(py * width + px) += 1

    // set image intensity depending on the number of bodies in the pixel
    for y <- 0 until height; x <- 0 until width do
      val count = pixels(y * width + x)
      val intensity = if count > 0 then math.min(255, 70 + count * 50) else 0
      val color = (255 << 24) | (intensity << 16) | (intensity << 8) | intensity
      img.setRGB(x, y, color)

    // for debugging purposes, if the number of bodies is small, output their locations
    val g = img.getGraphics.asInstanceOf[Graphics2D]
    g.setColor(Color.GRAY)
    if model.bodies.length < 350 then for b <- model.bodies do
      def round(x: Float) = (x * 100).toInt / 100.0f
      val px = ((b.x - model.screen.minX) / model.screen.width * width).toInt
      val py = ((b.y - model.screen.minY) / model.screen.height * height).toInt
      if px >= 0 && px < width && py >= 0 && py < height then
        g.drawString(s"${round(b.x)}, ${round(b.y)}", px, py)

    // render quad if necessary
    if model.shouldRenderQuad then
      g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
      val green = Color(0, 225, 80, 150)
      val red = Color(200, 0, 0, 150)
      g.setColor(green)
      def drawQuad(depth: Int, quad: Quad): Unit =
        def drawRect(fx: Float, fy: Float, fsz: Float, q: Quad, fill: Boolean = false): Unit =
          val x = ((fx - model.screen.minX) / model.screen.width * width).toInt
          val y = ((fy - model.screen.minY) / model.screen.height * height).toInt
          val w = ((fx + fsz - model.screen.minX) / model.screen.width * width).toInt - x
          val h = ((fy + fsz - model.screen.minY) / model.screen.height * height).toInt - y
          g.drawRect(x, y, w, h)
          if fill then g.fillRect(x, y, w, h)
          if depth <= 5 then g.drawString("#:" + q.total, x + w / 2, y + h / 2)
        quad match
          case Fork(nw, ne, sw, se) =>
            val cx = quad.centerX
            val cy = quad.centerY
            val sz = quad.size
            drawRect(cx - sz / 2, cy - sz / 2, sz / 2, nw)
            drawRect(cx - sz / 2, cy, sz / 2, sw)
            drawRect(cx, cy - sz / 2, sz / 2, ne)
            drawRect(cx, cy, sz / 2, se)
            drawQuad(depth + 1, nw)
            drawQuad(depth + 1, ne)
            drawQuad(depth + 1, sw)
            drawQuad(depth + 1, se)
          case Empty(_, _, _) | Leaf(_, _, _, _) =>
            // done
      drawQuad(0, model.quad)

    gcan.drawImage(img, 0, 0, null)

  // zoom on mouse rotation
  addMouseWheelListener(new MouseAdapter {
    override def mouseWheelMoved(e: MouseWheelEvent): Unit = {
      val rot = e.getWheelRotation
      val cx = model.screen.centerX
      val cy = model.screen.centerY
      val w = model.screen.width
      val h = model.screen.height
      val factor = {
        if rot > 0 then 0.52f
        else if rot < 0 then 0.48f
        else 0.5f
      }
      model.screen.minX = cx - w * factor
      model.screen.minY = cy - h * factor
      model.screen.maxX = cx + w * factor
      model.screen.maxY = cy + h * factor
      repaint()
    }
  })

  // reset the last known mouse drag position on mouse press
  var xlast = Int.MinValue
  var ylast = Int.MinValue
  addMouseListener(new MouseAdapter {
    override def mousePressed(e: MouseEvent): Unit = {
      xlast = Int.MinValue
      ylast = Int.MinValue
    }
  })

  // update the last known mouse drag position on mouse drag,
  // update the boundaries of the visible area
  addMouseMotionListener(new MouseMotionAdapter {
    override def mouseDragged(e: MouseEvent): Unit = {
      val xcurr = e.getX
      val ycurr = e.getY
      if xlast != Int.MinValue then {
        val xd = xcurr - xlast
        val yd = ycurr - ylast
        val w = model.screen.width
        val h = model.screen.height
        val cx = model.screen.centerX - xd * w / 1000
        val cy = model.screen.centerY - yd * h / 1000
        model.screen.minX = cx - w / 2
        model.screen.minY = cy - h / 2
        model.screen.maxX = cx + w / 2
        model.screen.maxY = cy + h / 2
        println(model.screen)
      }
      xlast = xcurr
      ylast = ycurr
      repaint()
    }
  })

