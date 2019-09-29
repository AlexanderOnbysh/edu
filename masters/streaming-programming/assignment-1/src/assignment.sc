import scala.annotation.tailrec

/*
 Problem 1
 Write a function that takes three numbers as arguments and
 returns the sum of the squares of the two larger numbers.
*/

def func1(a: Int, b: Int, c: Int): Int = {
  if ((a < b) & (a < c)) return b * b + c * c
  if ((b < a) & (b < c)) return a * a + c * c
  a * a + b * b
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

/*
 Problem 5
 Write a function to compute the factorial of the given
 number using recursive function. Rewrite it to be tail-recursive.
*/

def factorial(n: Int): Int = n match {
  case 1 => 1
  case _ => n * factorial(n - 1)
}

def factorial_tail(n: Int): Int = {
  @tailrec
  def factorial_with_acc(n: Int, acc: Int): Int = n match {
    case 1 => acc
    case _ => factorial_with_acc(n - 1, n * acc)
  }

  factorial_with_acc(n, 1)
}

factorial(5)
factorial_tail(5)

/*
 Problem 6
 Write a function to compute n-th term of the Fibonacci
 sequence. Write both recursive and tail-recursive functions.
*/

def fib(n: Int): Int = n match {
  case 1 | 2 => 1
  case _ => fib(n - 1) + fib(n - 2)
}

def fib_tail(n: Int): Int = {
  @tailrec
  def fib_inner(a1: Int, a2: Int, n: Int): Int = n match {
    case 3 => a1 + a2
    case _ => fib_inner(a2, a1 + a2, n - 1)
  }

  n match {
    case 1 | 2 => 1
    case _ => fib_inner(1, 1, n)
  }
}

fib(10)
fib_tail(10)

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
  def pascal_inner(l: List[Int], state: List[Int]): List[Int] = l match {
    case a :: b :: tail => pascal_inner(b :: tail, a + b :: state)
    case _ => 0 :: state
  }

  println(state.head.toString())

  height match {
    case 1 => state
    case _ => pascal(height - 1, pascal_inner(state.head, List(0)) :: state)
  }
}


pascal(100)

/*
 Problem 8
 Write a function that tests a given number for primality.
*/


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


def eval(l: List[String]): Int = {
  l match {
    case a :: Nil => a.toInt
    case a :: "+" :: tail => a.toInt + eval(tail)
    case a :: "*" :: b :: "*" :: tail => a.toInt * b.toInt * eval(tail)
    case a :: "*" :: b :: "+" :: tail => a.toInt * b.toInt + eval(tail)
    case a :: "*" :: b :: tail => a.toInt * b.toInt + eval(tail)
    case Nil => 0
    case _ => -1
  }
}


eval(List("1", "+", "2", "+", "4"))
eval(List("2", "*", "2"))
eval(List("2", "*", "2", "*", "3", "+", "10"))
eval(List())


//def​ ​eval​(​l​: ​List​[​String​])​:​ ​Int​ ​=​ ​???
//assert(eval(​List​(​"1"​, ​"+"​, ​"2"​, ​"*"​, ​"3"​)) ​==​ ​7​)
//assert(eval(​List​(​"5"​, ​"*"​, ​"3"​, ​"+"​, ​"1"​)) ​==​ ​16​)


def eval3(l: List[String], prev: Int): Int = {
  l match {
    case "+" :: tail => prev + eval3(tail, 0)
    case "*" :: tail => prev * eval3(tail, 0)
    case a :: tail => eval3(tail, a.toInt)
    case Nil => 1
  }
}

eval3(List("1", "+", "2", "*", "4"), 0)


def eval4(l: List[String], clean: List[Int]): List[Int] = {
  l match {
    case a :: "+" :: tail => eval4(tail, a :: clean)
    case a :: "*" :: tail =>
  }
}


def eval5(l: List[String], prev: Int): Int = l match {
  case "*" :: a :: tail => eval5(tail, a.toInt * prev)
  case "+" :: tail => prev.toInt + eval5(tail, 1)
  case a :: tail => 1
}

/*
ta

1 + 2 * 4

1 + eval(2 * 4)
1 + 2 * eval(4)
1 + 2 * 4 <-

1 * 2 + 3 * 4

1
prev 1
* 2
prev 1 * 2
+






*/




















