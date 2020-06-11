import org.scalatest.funsuite.AnyFunSuite

class PageTest extends AnyFunSuite {

  test("index") {
    assert(Page.index ==
      "<html><head><meta charset=\"utf8\" /></head>" +
        "<body><button id=\"draw-button\" type=\"button\" onclick=\"draw()\">Draw shapes</button>" +
        "<script src=\"shapes-client-fastopt.js\"></script>" +
        "</body></html>"
    )
  }

}
