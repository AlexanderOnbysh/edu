import scala.annotation.tailrec

/**
 * Created by Alexander Onbysh 03.11.2019
 */
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

object Exercise4 extends App {
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

}
