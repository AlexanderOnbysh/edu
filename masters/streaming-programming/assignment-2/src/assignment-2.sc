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

