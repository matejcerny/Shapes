import org.scalajs.dom
import org.scalajs.dom.raw.Node

import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("Client")
object Client extends RenderMethods {

  def main(): Unit = ()

  def paragraph(text: String): Node =
    dom.document
      .createElement("p")
      .appendChild(
        dom.document.createTextNode(text)
      )

  @JSExportTopLevel("draw")
  def draw(): Unit = {
    clearCanvas()

    Shape(Rectangle(100, 200)).render()
    Shape(Circle(150)).render()
    Shape(Square(150)).render()
  }

}
