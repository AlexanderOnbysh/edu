import com.sun.net.httpserver.Authenticator.Success

import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._


/**
 * Created by Alexander Onbysh 23.12.2019
 */
object FutureFibonacci extends App {
  def time[R](block: => R, text: String = "Run test", rounds: Int = 10): Unit = {

    var times: Long = 0
    for (_ <- 1 to rounds) {
      val t0 = System.nanoTime()
      val result = block
      times += System.nanoTime() - t0
    }
    println(s"$text: " + s"${times / rounds * 0.000001}" + " ms")
  }

  def fib(n: Long): Long = n match {
    case 0 | 1 => n
    case other if other > 0 => fib(n - 1) + fib(n - 2)
    case _ => throw new IllegalArgumentException("n should be positive")
  }

  def parallelFib(n: Long): Long = n match {
    case 0 | 1 => n
    case other if other > 0 => {
      val f1 = Future(parallelFib(n - 1))
      val f2 = Future(parallelFib(n - 2))
      Await.result(f1, Duration.Inf) + Await.result(f2, Duration.Inf)
    }
    case _ => throw new IllegalArgumentException("n should be positive")
  }

  time(
    fib(20),
    text="Simple recursive fibonacci"
  )
  time(
    parallelFib(20),
    text="Parallel recursive fibonacci"
  )

  assert(fib(0) == parallelFib(0))
  assert(fib(1) == parallelFib(1))
  assert(fib(2) == parallelFib(2))
  assert(fib(20) == parallelFib(20))
}

