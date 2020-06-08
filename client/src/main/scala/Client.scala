import org.scalajs.dom
import org.scalajs.dom.raw.Node

import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("Client")
object Client extends DrawMethods {

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
    Shapes.Rectangle(300, 300).drawShape()
  }

}
