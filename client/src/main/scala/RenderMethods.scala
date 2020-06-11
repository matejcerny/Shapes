import org.scalajs.dom
import org.scalajs.dom.CanvasRenderingContext2D
import org.scalajs.dom.html.Canvas

import scala.util.Random.between

trait RenderMethods {

  private val canvas = initCanvas
  private val ctx = initCtx
  private val colors = Seq("#F6A7C1", "#70AE98", "#5E96AE", "#9DABDD", "#E6B655", "#8AC0DE")

  private def initCanvas: Canvas = {
    val canvas = dom.document.createElement("canvas").asInstanceOf[Canvas]
    canvas.width = dom.window.innerWidth.toInt
    canvas.height = dom.window.innerHeight.toInt
    dom.document.body.appendChild(canvas)
    canvas
  }
  private def initCtx: CanvasRenderingContext2D =
    canvas.getContext("2d").asInstanceOf[CanvasRenderingContext2D]

  def clearCanvas(): Unit = ctx.clearRect(0, 0, canvas.width, canvas.height)
  private def randomPosition(): Point = Point(between(0, canvas.width), between(0, canvas.height))
  private def randomColor(): String = colors(between(0, colors.size))

  implicit class RenderShape[T: Render](shape: T) {
    def render(): Unit = implicitly[Render[T]].render(randomPosition(), shape)
  }

  implicit val renderShape: Render[Shape] = (position: Point, shape: Shape) => {
    val x = if (position.x + shape.width > canvas.width) canvas.width - shape.width else position.x
    val y = if (position.y + shape.height > canvas.height) canvas.height - shape.height else position.y
    ctx.beginPath()

    shape.shapeType match {
      case Circle(radius) => ctx.arc(x + radius, y + radius, radius, 0, 360)
      case Square(_) => ctx.rect(x, y, shape.width, shape.height)
      case Rectangle(_, _) | Square(_) => ctx.rect(x, y, shape.width, shape.height)
    }

    ctx.closePath()
    ctx.fillStyle = randomColor()
    ctx.fill()
  }

}
