/**
 * Created by Alexander Onbysh 07.12.2019
 */
sealed trait Tree

case class Node(value: Int, left: Tree, right: Tree) extends Tree

case object End extends Tree
