import Template._

object Page {

  def index: String = page(Seq(drawButton)).render

}
