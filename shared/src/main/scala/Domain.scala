case class Point(x: Double, y: Double)

sealed trait ShapeType
// cannot be in ShapeType wrapper object because of JS
case class Circle(radius: Int) extends ShapeType
case class Square(a: Int) extends ShapeType
case class Rectangle(a: Int, b: Int) extends ShapeType

case class Shape(shapeType: ShapeType) {
  val (width, height): (Int, Int) = shapeType match {
    case Circle(radius) => (radius * 2, radius * 2)
    case Square(a) => (a, a)
    case Rectangle(a, b) => (a, b)
  }
}