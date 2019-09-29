// Ex #2
// Write a function to filter a given list of integers
// using for-comprehension
def filter(list: List[Int], f: Int => Boolean): List[Int] =
  for (i <- list; if f(i)) yield i

val ints = List(1, 2, 3, 4, 5, 6, 7, 8, 9)
val f: Int => Boolean = _ % 2 == 0
assert(filter(ints, f) == List(2, 4, 6, 8))

def head(list: List[Int]): Int = list.head
head(1 :: 2 :: 3 :: Nil)

def sum(list: List[Int]): Int = list.sum

sum(List(1, 2, 3))

// Ex #2.1
// Using randomList - generate 10x10 table of random numbers,
// filter negatives using for comprehension and filter function above
// output a table using println
