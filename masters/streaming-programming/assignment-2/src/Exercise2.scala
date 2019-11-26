/**
 * Created by Alexander Onbysh 03.11.2019
 */
/*
Problem 2

Let f and g be two one-argument functions.
Then composition f after g is defined to be
the function x -> f(g(x)). Define a function 'compose'
that implements composition.
*/

object Exercise2 extends App {
  def compose[A, B, C](f: B => C, g: A => B): A => C = {
    x: A => f(g(x))
  }

  def pow2(a: Int): Int = a * a

  def inc(a: Int): Int = a + 1

  def toS(a: Int): String = a.toString

  assert(compose(pow2, inc)(2) == 9)
  assert(compose(inc, pow2)(2) == 5)
  assert(compose(toS, pow2)(2) == "4")


}
