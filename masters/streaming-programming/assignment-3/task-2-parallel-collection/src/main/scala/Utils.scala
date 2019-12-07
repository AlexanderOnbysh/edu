

/**
 * Created by Alexander Onbysh 07.12.2019
 */


object Utils {

  def time[R](block: => R, text: String = "Run test", rounds: Int = 10, warmup: Int = 10): Unit = {

    for (_ <- 1 to warmup) block

    var times: Long = 0
    for (_ <- 1 to rounds) {
      val t0 = System.nanoTime()
      val result = block
      times += System.nanoTime() - t0
    }
    println(s"$text: " + s"${times / rounds * 0.000001}" + " ms")
  }
}
