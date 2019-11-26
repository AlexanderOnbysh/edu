/**
 * Created by Alexander Onbysh 03.11.2019
 */
/*
Problem 5

Implement a representation for shapes in the
plane - rectangles and circles. Implement functions
to estimate perimeter and area of the shape.
*/

trait Figure {
  val perimeter: Double
  val area: Double
}

class Coordinate(val x: Double, val y: Double) {
  //  L1 distance
  def <->(other: Coordinate): Double = Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2))

  override def toString: String = s"($x, $y)"
}

object Coordinate {
  def apply(x: Double, y: Double): Coordinate = new Coordinate(x, y)
}

class Rectangle(val a: Coordinate, val b: Coordinate,
                val c: Coordinate, val d: Coordinate) extends Figure {

  val perimeter: Double = (a <-> b) + (b <-> c) + (c <-> d) + (a <-> d)
  val area: Double = (a <-> b) * (b <-> c)

  override def toString: String = s"$a, $b, $c, $d"
}

object Rectangle {
  def apply(a: Coordinate, b: Coordinate, c: Coordinate, d: Coordinate): Option[Rectangle] =
    if ((a <-> b == c <-> d) && (b <-> c == a <-> d)) Some(new Rectangle(a, b, c, d))
    else None
}

class Circle(center: Coordinate, radius: Double) extends Figure {

  val perimeter: Double = 2 * Math.PI * radius
  val area: Double = Math.PI * Math.pow(radius, 2)

  override def toString: String = s"c: $center, R: $radius"
}

object Circle {
  def apply(center: Coordinate, radius: Double): Option[Circle] =
    if (radius > 0) Some(new Circle(center, radius))
    else None
}

object Exercise5 extends App {
  def ~=(x: Double, y: Double, precision: Double): Boolean = {
    if ((x - y).abs < precision) true else false
  }

  assert(Rectangle(Coordinate(0, 0), Coordinate(10, 0),
    Coordinate(10, 3), Coordinate(0, 3)).get.area == 30)
  assert(Rectangle(Coordinate(0, 0), Coordinate(10, 0),
    Coordinate(10, 3), Coordinate(0, 3)).get.perimeter == 26)

  assert(~=(Circle(Coordinate(0, 0), 10).get.area, 314.15926535, 0.00001))
  assert(~=(Circle(Coordinate(0, 0), 10).get.perimeter, 62.83185307, 0.00001))


}
