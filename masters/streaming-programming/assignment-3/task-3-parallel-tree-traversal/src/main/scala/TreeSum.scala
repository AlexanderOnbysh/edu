import java.util.concurrent.RecursiveTask

import scala.annotation.tailrec

/**
 * Created by Alexander Onbysh 07.12.2019
 */
object TreeSum {

  def parallelSum(tree: Tree): Int = {
    class ParallelTreeSum(tree: Tree, acc: Int) extends RecursiveTask[Int] {
      override def compute(): Int = tree match {
        case Node(value, left, right) => {
          val t1 = new ParallelTreeSum(left, acc)
          val t2 = new ParallelTreeSum(right, acc)
          t1.fork()
          value + t2.compute() + t1.join()
        }
        case End => acc
      }
    }

    new ParallelTreeSum(tree, 0).compute()
  }

  def nonParallelSum(tree: Tree): Int = {
    @tailrec
    def inner(tree: List[Tree], acc: Int): Int = tree match {
      case Nil => acc
      case Node(value, left, right) :: tail => inner(left :: right :: tail, acc + value)
      case End :: tail => inner(tail, acc)
    }

    inner(List(tree), 0)
  }

}
