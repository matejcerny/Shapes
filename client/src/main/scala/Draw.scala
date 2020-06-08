trait Draw[T] {
  def drawShape(position: Shapes.Point, shape: T): Unit
}