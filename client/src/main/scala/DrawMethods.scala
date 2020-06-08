import Shapes.{Point, Rectangle}
import org.scalajs.dom
import org.scalajs.dom.ext.Color
import org.scalajs.dom.html.Canvas

import scala.util.Random.between

trait DrawMethods {

  private val canvas = dom.document.createElement("canvas").asInstanceOf[Canvas]
  canvas.width = dom.window.innerWidth.toInt
  canvas.height = dom.window.innerHeight.toInt
  dom.document.body.appendChild(canvas)

  private val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]

  def clearCanvas(): Unit = ctx.clearRect(0, 0, canvas.width, canvas.height)
  private def randomPosition(): Point = Point(between(0, canvas.width), between(0, canvas.height))
  private def randomColor(): String = Color.all(between(0, Color.all.size)).toHex

  implicit class DrawShape[T: Draw](shape: T) {
    def drawShape(): Unit = implicitly[Draw[T]].drawShape(randomPosition(), shape)
  }

  implicit val drawRectangle: Draw[Rectangle] = (position: Point, shape: Rectangle) => {
    ctx.beginPath()
    ctx.moveTo(position.x, position.y)
    ctx.lineTo(shape.width, position.y)
    ctx.lineTo(shape.width, shape.height)
    ctx.lineTo(position.x, shape.height)
    ctx.closePath()
    ctx.fillStyle = randomColor()
    ctx.fill()
  }

}
