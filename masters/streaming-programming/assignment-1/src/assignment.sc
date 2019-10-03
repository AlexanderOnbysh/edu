import scala.annotation.tailrec

/*
 Problem 1
 Write a function that takes three numbers as arguments and
 returns the sum of the squares of the two larger numbers.
*/

def func1(a: Int, b: Int, c: Int): Int = {
  if ((a < b) & (a < c)) b * b + c * c
  else if ((b < a) & (b < c)) a * a + c * c
  else a * a + b * b
}

assert(func1(1, 2, 3) == 13)
assert(func1(2, 3, 1) == 13)
assert(func1(3, 2, 1) == 13)

/*
 Problem 2
 Write a function that computes the square root of a given
 number using Newton's method. Define two functions: 'sqrt'
 and 'goodEnough'. 'good Enough' should check whether given
 approximation obtained after some step is good enough given
 some difference.
*/

def goodEnough(eps: Double = 1e-10) =
  (a: Double, b: Double) => Math.abs(a - b) < eps


def sqrt(a: Double,
         criteria: (Double, Double) => Boolean = goodEnough()): Double = {

  @tailrec
  def sqrtInner(a: Double, x1: Double): Double = {
    val x2 = 1.0 / 2 * (x1 + a / x1)
    if (criteria(x1, x2)) x2 else sqrtInner(a, x2)
  }

  sqrtInner(a, a)
}

val comp = goodEnough(1e-3)
assert(comp(sqrt(1), 1))
assert(comp(sqrt(2), 1.414))
assert(comp(sqrt(4), 2))

/*
 Problem 3
 Write your own 'if' statement that takes a predicate, then-clause
 and else-clause as parameters (e.g. function ​myIf(..))​ .
 Try to use this custom 'if' statement in the previous function
 that computes square root. Does it work fine?
 If not, try to explain why?
*/

def myif(predicate: Boolean,
         my_then: () => Unit,
         my_else: () => Unit): Unit = predicate match {
  case true => my_then()
  case false => my_else()
}


myif(1 == 2, () => println("One"), () => println("Two"))
myif(1 == 1, () => println("One"), () => println("Two"))

/*
 Problem 4
 Newton’s method for cube roots is based on the fact that
 if _y_ is an approximation to the cube root of _x_,
 then a better approximation is given by the value:
 ((x/y^2) + 2y)/3
 Use this formula to implement a function to estimate the
 cube root of the given number.
*/

def cubeRoot(a: Double,
             criteria: (Double, Double) => Boolean = goodEnough()): Double = {

  @tailrec
  def cubeRootInner(a: Double, x1: Double): Double = {
    val x2 = 1.0 / 3 * (2 * x1 + a / Math.pow(x1, 2))
    if (criteria(x1, x2)) x2 else cubeRootInner(a, x2)
  }

  cubeRootInner(a, a)
}

assert(comp(cubeRoot(1), 1))
assert(comp(cubeRoot(27), 3))
assert(comp(cubeRoot(10), 2.154))


/*
 Problem 5
 Write a function to compute the factorial of the given
 number using recursive function. Rewrite it to be tail-recursive.
*/

def factorial(n: Int): Int = n match {
  case 1 => 1
  case _ => n * factorial(n - 1)
}

def factorialTail(n: Int): Int = {
  @tailrec
  def accFactorial(n: Int, acc: Int): Int = n match {
    case 1 => acc
    case _ => accFactorial(n - 1, n * acc)
  }

  accFactorial(n, 1)
}

factorial(5)
factorialTail(5)

/*
 Problem 6
 Write a function to compute n-th term of the Fibonacci
 sequence. Write both recursive and tail-recursive functions.
*/

def fib(n: Int): Int = n match {
  case 1 | 2 => 1
  case _ => fib(n - 1) + fib(n - 2)
}

def fibTail(n: Int): Int = {
  @tailrec
  def fibInner(a1: Int, a2: Int, n: Int): Int = n match {
    case 3 => a1 + a2
    case _ => fibInner(a2, a1 + a2, n - 1)
  }

  n match {
    case 1 | 2 => 1
    case _ => fibInner(1, 1, n)
  }
}

fib(10)
fibTail(10)

/*
 Problem 7
 The following pattern of numbers is called Pascal’s triangle .
     1
    1 1
   1 2 1
  1 3 3 1
 1 4 6 4 1
 The numbers at the edge of the triangle are all 1, and each
 number inside the triangle is the sum of the two numbers above it.
 Write a function that computes elements of Pascal’s triangle.
*/

@tailrec
def pascal(height: Int,
           state: List[List[Int]] = List(List(0, 1, 0))
          ): List[List[Int]] = {
  @tailrec
  def pascalInner(l: List[Int], state: List[Int]): List[Int] = l match {
    case a :: b :: tail => pascalInner(b :: tail, a + b :: state)
    case _ => 0 :: state
  }

  height match {
    case 1 => state
    case _ => pascal(height - 1, pascalInner(state.head, List(0)) :: state)
  }
}

def displayPascal(pascal: List[List[Int]]): Unit =
  for ((line, offset) <-
         pascal.reverse zip (pascal.length to 1 by -1))
    print(" " * offset + line.mkString(" ") + "\n")


val p = pascal(5)
displayPascal(p)

/*
 Problem 8
 Write a function that tests a given number for primality.
*/
def isPrime(n: Int): Boolean = (n > 1) && !(2 to sqrt(n).toInt).exists(x => n % x == 0)

assert(isPrime(2))
assert(isPrime(3))
assert(!isPrime(4))
assert(!isPrime(9))
assert(isPrime(13))


/*
 Problem 9
 Simple Expression parser.
 Build an expression evaluator that works on tokens of strings
 composed of integers and binary operators + and * (see example)
 Grammar:
   expression -> number {operator number}*
   operator -> ‘+’ | ‘*’
   number -> regex([0-9]+)
 Hints:
   - use pattern matching to match operators and numbers in List
   - use .toInt to parse number
     "1".toInt == 1
*/


/** Shunting-yard algorithm */
def eval(l: List[String]): Int = {

  val operatorRegex = "[\\*\\+]"
  val numberRegex = "[0-9]+"

  def priority(operator: String): Int = operator match {
    case "+" => 1
    case "*" => 2
  }

  def operate(op: String, a: Int, b: Int): Int = op match {
    case "+" => a + b
    case "*" => a * b
  }

  @tailrec
  def evalInner(l: List[String],
                valueStack: List[Int],
                operatorStack: List[String]): Int = l match {
    case operator :: lTail if operator.matches(operatorRegex) =>
      operatorStack match {
        case Nil => evalInner(lTail, valueStack, operator :: operatorStack)
        case op :: opTail =>
          if (priority(operator) > priority(op))
            evalInner(lTail, valueStack, operator :: op :: opTail)
          else valueStack match {
            case Nil => evalInner(lTail, valueStack, operator :: operatorStack)
            case v1 :: v2 :: vTail => evalInner(l, operate(op, v1, v2) :: vTail, opTail)
            case _ => throw new Exception(s"Two sequential operators: ${op.toString} ${operator.toString}")
          }
      }

    case number :: tail if number.matches(numberRegex) => evalInner(tail, number.toInt :: valueStack, operatorStack)

    case Nil =>
      (valueStack, operatorStack) match {
        case (Nil, _) => 0
        case (value :: Nil, _) => value
        case (v1 :: v2 :: vTail, op :: opTail) => evalInner(Nil, operate(op, v1, v2) :: vTail, opTail)
        case _ => throw new Exception("Two sequential numbers")
      }

    case unknown => throw new Exception(s"Unknown value ${unknown}")
  }

  evalInner(l, List(), List())
}


assert(eval(List("1")) == 1)
assert(eval(List("1", "+", "1", "+", "3")) == 5)
assert(eval(List("1", "*", "2", "*", "3")) == 6)
assert(eval(List("1", "+", "1", "*", "10")) == 11)
assert(eval(List("2", "*", "3", "+", "10", "*", "2")) == 26)
