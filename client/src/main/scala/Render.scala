trait Render[T] {
  def render(position: Point, shape: T): Unit
}