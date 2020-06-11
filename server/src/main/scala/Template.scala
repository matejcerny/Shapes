import scalatags.Text.all._

object Template {

  def page(bodyContent: Seq[Tag]): Tag =
    html(
      head(
        meta(charset := "utf8"),
      ),
      body(
        bodyContent,
        script(src := "shapes-client-fastopt.js")
      )
    )

  val shapesChoice: Tag =
    fieldset(
      id := "shapes",
      legend("Shapes"),
      input(`type`:="radio", name:="shape", value:="Circle", id:="circle"),
      label(`for`:="circle", "Circle"), br,
      input(`type`:="radio", name:="shape", value:="Square", id:="square"),
      label(`for`:="square", "Square"), br,
      input(`type`:="radio", name:="shape", value:="Rectangle", id:="rectangle"),
      label(`for`:="rectangle", "Rectangle")
    )

  val drawButton: Tag =
    button(
      id := "draw-button",
      `type` := "button",
      onclick := "draw()",
      "Draw shapes"
    )

}
