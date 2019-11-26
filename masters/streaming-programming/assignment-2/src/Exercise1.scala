/**
 * Created by Alexander Onbysh 03.11.2019
 */
/*
Problem 1

Write a function that takes an okother zero-arguments
function as parameter and executes it.
*/

object Exercise1 extends App {
  def func[T](f: () => T): T = f()

  assert(func(() => 1) == 1)
  assert(func(() => "10") == "10")
}