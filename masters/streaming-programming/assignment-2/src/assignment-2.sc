import scala.annotation.tailrec

/*
Problem 1

Write a function that takes an okother zero-arguments
function as parameter and executes it.
*/

def func[T](f: () => T): T = f()

assert(func(() => 1) == 1)
assert(func(() => "10") == "10")

/*
Problem 2

Let f and g be two one-argument functions.
Then composition f after g is defined to be
the function x -> f(g(x)). Define a function 'compose'
that implements composition.
*/

def compose[A, B, C](f: B => C, g: A => B): A => C = {
  x: A => f(g(x))
}

def pow2(a: Int): Int = a * a
def inc(a: Int): Int = a + 1
def toS(a: Int): String = a.toString

assert(compose(pow2, inc)(2) == 9)
assert(compose(inc, pow2)(2) == 5)
assert(compose(toS, pow2)(2) == "4")


/*
Problem 3

Write a function that takes as inputs a function that
computes f and a positive integer n and returns the
function that computes the n-th repeated application of f.
*/

def repeat[A](f: A => A, n: Int = 1): A => A = {
  @tailrec
  def inner(f: A => A, n: Int, buff: A => A): A => A = n match {
    case 1 => buff
    case _ => inner(f, n - 1, compose(buff, f))
  }

  inner(f, n, f)
}

assert(repeat(inc, 10)(0) == 10)
assert(repeat(pow2, 2)(2) == 16)

/*
Problem 4

Implement rational-number arithmetic system.

'Rational' class - takes two parameters n and d
and returns the rational number whose numerator
is the integer n and whose denominator is the integer d.

'numer' returns the numerator of the rational nubmer x
'denom' returns the denominator of the rational number x

implement 'add', 'sub', 'mul' and 'div' and 'equal'
functions for Rational class.
Implement ‘apply’ method in the companion object of
the Rational class - this is going to be a builder
for rational numbers. Make it  return
‘Option’ type - based on the value of
denominator (0 or non-0).

*/

class Rational(var n: Int, var d: Int) {
  require(d != 0, "Denominator could not be 0")

  private val g = this.gcd(n, d)
  val numer = this.n / g * d.sign
  val denom = Math.abs(this.d) / g

  @tailrec
  private def gcd(a: Int, b: Int): Int = if (b == 0) a else this.gcd(b, a % b)

  def +(other: Rational): Rational = new Rational(this.numer * other.denom + other.numer * this.denom, this.denom * other.denom)

  def -(other: Rational): Rational = this + new Rational(other.numer * -1, other.denom)

  def *(other: Rational): Rational = new Rational(this.numer * other.numer, this.denom * other.denom)

  def /(other: Rational): Rational = this * new Rational(other.denom, other.numer)

  def ==(other: Rational): Boolean = this.numer == other.numer && this.denom == other.denom

  override def toString: String = s"$numer / $denom"
}

object Rational {
  def apply(n: Int, d: Int): Option[Rational] = d match {
    case 0 => None
    case _ => Some(new Rational(n, d))
  }
}

// +
assert(Rational(1, 2).get + Rational(3, 4).get == Rational(5, 4).get)
assert(Rational(1, 2).get + Rational(3, 4).get == Rational(10, 8).get)
// -
assert(Rational(1, 2).get - Rational(3, 4).get == Rational(-1, 4).get)
// *
assert(Rational(1, 2).get * Rational(3, 4).get == Rational(3, 8).get)
// /
assert(Rational(1, 2).get / Rational(3, 4).get == Rational(4, 6).get)
// edge cases
assert(Rational(1, 0).getOrElse(None) == None)


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

def ~=(x: Double, y: Double, precision: Double): Boolean = {
  if ((x - y).abs < precision) true else false
}

assert(Rectangle(Coordinate(0, 0), Coordinate(10, 0),
  Coordinate(10, 3), Coordinate(0, 3)).get.area == 30)
assert(Rectangle(Coordinate(0, 0), Coordinate(10, 0),
  Coordinate(10, 3), Coordinate(0, 3)).get.perimeter == 26)

assert(~=(Circle(Coordinate(0, 0), 10).get.area, 314.15926535, 0.00001))
assert(~=(Circle(Coordinate(0, 0), 10).get.perimeter, 62.83185307, 0.00001))

/*
Problem 6

Implement Linked List (using nested structure with Nil,Cons case classes).
You should provide the following method for this data structure:

'length', 'add', 'remove', 'reverse', 'last'

Make your data structure generic over the type of elements
contained in it. Make it covariant over type of elements.
*/

class Node[A](val element: A, var next: Option[Node[A]])

class LinkedList[A] {

  private var head: Option[Node[A]] = None

  def this(node: Node[A]) {
    this()
    this.head = Some(node)
  }

  @tailrec
  private def buildNodes(lastNode: Option[Node[A]], values: Seq[A]): Option[Node[A]] =
    values match {
      case Seq() => lastNode
      case Seq(value, tail@_*) => lastNode match {
        case None => buildNodes(Some(new Node(value, None)), tail)
        case Some(_) => buildNodes(Some(new Node(value, lastNode)), tail)
      }
    }

  def this(values: A*) {
    this()
    head = buildNodes(None, values.reverse)
  }

  def this(values: Iterator[A]) {
    this()
    head = buildNodes(None, values.toList.reverse)
  }

  override def toString: String = {
    @tailrec
    def inner(node: Option[Node[A]], buff: List[String] = List()): List[String] =
      node match {
        case None => buff
        case Some(x) => inner(x.next, s"${x.element}" :: buff)
      }

    inner(head).reverse.mkString(s"${this.getClass.getName}(", ", ", ")")
  }

  def apply(i: Int): A = {
    @tailrec
    def inner(node: Node[A], counter: Int): A = node.next match {
      case None => throw new IndexOutOfBoundsException()
      case Some(x) => inner(x, counter - 1)
    }

    head match {
      case None => throw new IndexOutOfBoundsException()
      case Some(x) => inner(x, i)
    }
  }

  def iterator: Iterator[A] = Iterator
    .iterate(head)(_.flatMap(_.next))
    .takeWhile(_.nonEmpty)
    .flatten
    .map(_.element)

  override def equals(other: Any): Boolean = other match {
    case other: LinkedList[A] => this.iterator.zip(other.iterator).forall(x => x._1 == x._2) && (this.iterator.length == other.iterator.length)
    case _ => false
  }


  def length: Int = {
    @tailrec
    def inner(node: Option[Node[A]], counter: Int = 0): Int = node match {
      case None => counter
      case Some(x) => inner(x.next, counter + 1)
    }

    inner(head)
  }

  def addFront(value: A): LinkedList[A] = head match {
    case None => new LinkedList[A](value)
    case Some(x) => new LinkedList[A](new Node(value, Some(x)))
  }

  def add(value: A): LinkedList[A] = new LinkedList[A](this.iterator ++ Iterator(value))

  def remove(n: Int*): LinkedList[A] = {
    val indexes = n.toSet
    new LinkedList[A](
      for {
        (el, i) <- this.iterator.zipWithIndex
        if !indexes.contains(i)
      } yield el
    )
  }

  def reverse: LinkedList[A] = new LinkedList[A](this.iterator.toList.reverseIterator)

  def last: Option[A] = if (head.isEmpty) None
  else Some(this.iterator.slice(this.length - 1, this.length).next())

}


val l = new LinkedList[Int](1, 2, 3, 4, 5, 6)

assert(new LinkedList().length == 0)
assert(l.length == 6)

println(l.add(7))
assert(l.add(7) == new LinkedList[Int](1, 2, 3, 4, 5, 6, 7))
assert(l.add(7).add(8) == new LinkedList[Int](1, 2, 3, 4, 5, 6, 7, 8))

assert(l.addFront(0) == new LinkedList[Int](0, 1, 2, 3, 4, 5, 6))
assert(l.addFront(0).addFront(-1) == new LinkedList[Int](-1, 0, 1, 2, 3, 4, 5, 6))
assert(l.addFront(0).addFront(-1) == new LinkedList[Int](-1, 0, 1, 2, 3, 4, 5, 6))

assert(l.reverse == new LinkedList[Int](6, 5, 4, 3, 2, 1))
assert(new LinkedList[Int]().reverse == new LinkedList[Int]())

assert(l.remove(0, 3, 5) == new LinkedList[Int](2, 3, 5))
assert(l.remove(-10, 100) == new LinkedList[Int](1, 2, 3, 4, 5, 6))
