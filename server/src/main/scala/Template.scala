import scalatags.Text.all._

object Template {

  def page(bodyContent: Seq[Tag]): Tag = {
    html(
      head(
        meta(charset := "utf8"),
      ),
      body(
        bodyContent,
        script(src := "shapes-client-fastopt.js")
      )
    )
  }

  val drawButton: Tag = {
    button(
      id := "draw-button",
      `type` := "button",
      onclick := "draw()",
      "Draw shapes"
    )
  }

}
