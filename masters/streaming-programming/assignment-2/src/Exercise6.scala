import scala.annotation.tailrec

/**
 * Created by Alexander Onbysh 03.11.2019
 */
/*
Problem 6

Implement Linked List (using nested structure with Nil,Cons case classes).
You should provide the following method for this data structure:

'length', 'add', 'remove', 'reverse', 'last'

Make your data structure generic over the type of elements
contained in it. Make it covariant over type of elements.
*/

class Node[A](val element: A, var next: Option[Node[A]])

class LinkedList[A] {

  private var head: Option[Node[A]] = None

  def this(node: Node[A]) {
    this()
    this.head = Some(node)
  }

  @tailrec
  private def buildNodes(lastNode: Option[Node[A]], values: Seq[A]): Option[Node[A]] =
    values match {
      case Seq() => lastNode
      case Seq(value, tail@_*) => lastNode match {
        case None => buildNodes(Some(new Node(value, None)), tail)
        case Some(_) => buildNodes(Some(new Node(value, lastNode)), tail)
      }
    }

  def this(values: A*) {
    this()
    head = buildNodes(None, values.reverse)
  }

  def this(values: Iterator[A]) {
    this()
    head = buildNodes(None, values.toList.reverse)
  }

  override def toString: String = {
    @tailrec
    def inner(node: Option[Node[A]], buff: List[String] = List()): List[String] =
      node match {
        case None => buff
        case Some(x) => inner(x.next, s"${x.element}" :: buff)
      }

    inner(head).reverse.mkString(s"${this.getClass.getName}(", ", ", ")")
  }

  def apply(i: Int): A = {
    @tailrec
    def inner(node: Node[A], counter: Int): A = node.next match {
      case None => throw new IndexOutOfBoundsException()
      case Some(x) => inner(x, counter - 1)
    }

    head match {
      case None => throw new IndexOutOfBoundsException()
      case Some(x) => inner(x, i)
    }
  }

  def iterator: Iterator[A] = Iterator
    .iterate(head)(_.flatMap(_.next))
    .takeWhile(_.nonEmpty)
    .flatten
    .map(_.element)

  override def equals(other: Any): Boolean = other match {
    case other: LinkedList[A] => this.iterator.zip(other.iterator).forall(x => x._1 == x._2) && (this.iterator.length == other.iterator.length)
    case _ => false
  }


  def length: Int = {
    @tailrec
    def inner(node: Option[Node[A]], counter: Int = 0): Int = node match {
      case None => counter
      case Some(x) => inner(x.next, counter + 1)
    }

    inner(head)
  }

  def addFront(value: A): LinkedList[A] = head match {
    case None => new LinkedList[A](value)
    case Some(x) => new LinkedList[A](new Node(value, Some(x)))
  }

  def add(value: A): LinkedList[A] = new LinkedList[A](this.iterator ++ Iterator(value))

  def remove(n: Int*): LinkedList[A] = {
    val indexes = n.toSet
    new LinkedList[A](
      for {
        (el, i) <- this.iterator.zipWithIndex
        if !indexes.contains(i)
      } yield el
    )
  }

  def reverse: LinkedList[A] = new LinkedList[A](this.iterator.toList.reverseIterator)

  def last: Option[A] = if (head.isEmpty) None
  else Some(this.iterator.slice(this.length - 1, this.length).next())

}


object Exercise6 extends App {
  val l = new LinkedList[Int](1, 2, 3, 4, 5, 6)

  assert(new LinkedList().length == 0)
  assert(l.length == 6)

  println(l.add(7))
  assert(l.add(7) == new LinkedList[Int](1, 2, 3, 4, 5, 6, 7))
  assert(l.add(7).add(8) == new LinkedList[Int](1, 2, 3, 4, 5, 6, 7, 8))

  assert(l.addFront(0) == new LinkedList[Int](0, 1, 2, 3, 4, 5, 6))
  assert(l.addFront(0).addFront(-1) == new LinkedList[Int](-1, 0, 1, 2, 3, 4, 5, 6))
  assert(l.addFront(0).addFront(-1) == new LinkedList[Int](-1, 0, 1, 2, 3, 4, 5, 6))

  assert(l.reverse == new LinkedList[Int](6, 5, 4, 3, 2, 1))
  assert(new LinkedList[Int]().reverse == new LinkedList[Int]())

  assert(l.remove(0, 3, 5) == new LinkedList[Int](2, 3, 5))
  assert(l.remove(-10, 100) == new LinkedList[Int](1, 2, 3, 4, 5, 6))

}
