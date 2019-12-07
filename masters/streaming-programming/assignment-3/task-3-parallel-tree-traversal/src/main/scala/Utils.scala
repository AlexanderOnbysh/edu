import scala.collection.mutable

/**
 * Created by Alexander Onbysh 07.12.2019
 */


object Utils {

  def time[R](block: => R, text: String = "Run test", rounds: Int = 10): Unit = {

    var times: Long = 0
    for (_ <- 1 to rounds) {
      val t0 = System.nanoTime()
      val result = block
      times += System.nanoTime() - t0
    }
    println(s"$text: " + s"${times / rounds * 0.000001}" + " ms")
  }

  def getRandom: Int = scala.util.Random.nextInt % 100 + 1

  def generate(depth: Int): Tree = {
    //    Build random tree from leafs to root
    //    non recursive implementation

    assert(depth > 1, "Depth should be greater than 1")
    var q1 = mutable.Queue[Node]()
    val q2 = mutable.Queue[Node]()

    for (_ <- 1 to Math.pow(2, depth).toInt) q1.addOne(Node(getRandom, End, End))

    for (d <- depth - 1 to 0 by -1) {
      for (_ <- 1 to Math.pow(2, d).toInt) q2.addOne(Node(getRandom, q1.dequeue(), q1.dequeue()))
      q1 = q2
    }
    q1.dequeue()
  }
}

