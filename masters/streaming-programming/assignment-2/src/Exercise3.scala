import scala.annotation.tailrec

/**
 * Created by Alexander Onbysh 03.11.2019
 */
/*
Problem 3

Write a function that takes as inputs a function that
computes f and a positive integer n and returns the
function that computes the n-th repeated application of f.
*/
object Exercise3 extends App {
  def compose[A, B, C](f: B => C, g: A => B): A => C = {
    x: A => f(g(x))
  }

  def pow2(a: Int): Int = a * a

  def inc(a: Int): Int = a + 1

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
}
