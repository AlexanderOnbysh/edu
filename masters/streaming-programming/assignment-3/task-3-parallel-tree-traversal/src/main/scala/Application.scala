/**
 * Created by Alexander Onbysh 07.12.2019
 */

import Utils._


object Application extends App {
  println("Generating tree...")
  var tree = generate(10)

  println("Run perf test...")
  time(
    {
      TreeSum.nonParallelSum(tree)
    },
    "Non parallel implementation of tree sum (Depth = 10)"
  )

  println("Run perf test...")
  time(
    {
      TreeSum.parallelSum(tree)
    },
    "Parallel implementation of tree sum (Depth = 10)"
  )

  println("Generating tree...")
  tree = generate(24)

  println("Run perf test...")
  time(
    {
      TreeSum.nonParallelSum(tree)
    },
    "Non parallel implementation of tree sum (Depth = 23)"
  )

  println("Run perf test...")
  time(
    {
      TreeSum.parallelSum(tree)
    },
    "Parallel implementation of tree sum (Depth = 23)"
  )
}
