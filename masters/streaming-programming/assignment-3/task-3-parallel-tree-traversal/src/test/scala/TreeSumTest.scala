/**
 * Created by Alexander Onbysh 07.12.2019
 */

import org.scalatest._

class TreeSumTest extends FlatSpec with Matchers {

  it should "return value from Node" in {
    val tree = Node(1, End, End)
    TreeSum.nonParallelSum(tree) should be(1)
    TreeSum.parallelSum(tree) should be(1)
  }

  it should "return sum with Tree depth 2" in {
    val tree =
      Node(1,
        Node(2, End, End),
        Node(3, End, End)
      )
    TreeSum.nonParallelSum(tree) should be(6)
    TreeSum.parallelSum(tree) should be(6)
  }

  it should "return sum with Tree depth 3" in {
    val tree =
      Node(1,
        Node(2,
          Node(4, End, End),
          Node(5, End, End)
        ),
        Node(3,
          Node(6, End, End),
          End
        )
      )
    TreeSum.nonParallelSum(tree) should be(21)
    TreeSum.parallelSum(tree) should be(21)
  }
  it should "return sum with Tree depth 4" in {
    val tree =
      Node(1,
        Node(2,
          Node(4, End, End),
          Node(5, End, End)
        ),
        Node(3,
          Node(6,
            Node(7, End, End),
            End
          ),
          End
        )
      )
    TreeSum.nonParallelSum(tree) should be(28)
    TreeSum.parallelSum(tree) should be(28)
  }
}
