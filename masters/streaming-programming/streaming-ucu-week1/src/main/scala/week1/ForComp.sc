val lb = List(1, 2, 3, 4, 5)

/*
Ex #0
Using for-loop, build the list of squares of lb*/
val lb_squared = for (i <- lb) yield i * i

// Ex #0.1
// Enhance previous by filtering out even squares, leaving only odds
val lb_squared_odd = for (i <- lb if i % 2 == 0) yield i * i

// Enhance previous by println-ing every value
for (i <- lb if i % 2 == 0) println(i * i)

// Ex #1
// Write a function which for a given number
// returns a List of random integers of that length
def randomList(length: Int): List[Int] = {
  if (length <= 0) return List()

  val r = scala.util.Random
  (for (_ <- 1 to length) yield r.nextInt()).toList
}

randomList(10)
