package canvas

import org.scalajs.dom.{CanvasRenderingContext2D, document}
import org.scalajs.dom.html.Canvas

@main def smiley(): Unit =
  val canvasElement = document.createElement ("canvas").asInstanceOf[Canvas]
  canvasElement.width = 500
  canvasElement.height = 500
  document.body.appendChild(canvasElement)

  val graphics = canvasElement.getContext("2d").asInstanceOf[CanvasRenderingContext2D]
    
  def drawEye(x: Double, y: Double): Unit =
    graphics.beginPath()
    graphics.arc(x, y, 15, 0, math.Pi * 2)
    graphics.stroke()
    graphics.fillStyle = "blue"
    graphics.fill()
    
  def drawMouth(x: Double, y: Double): Unit =
    graphics.beginPath()
    graphics.arc(x, y, 200, math.Pi / 4, 3 * math.Pi / 4)
    graphics.lineWidth = 10
    graphics.lineCap = "round"
    graphics.strokeStyle = "red"
    graphics.stroke()
    
  drawEye(170, 150)
  drawEye(330, 150)
  drawMouth(250, 200)
end smiley
