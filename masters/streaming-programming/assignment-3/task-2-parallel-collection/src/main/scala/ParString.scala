import scala.collection.mutable
import scala.collection.parallel.{Combiner, SeqSplitter, immutable}


/**
 * Created by Alexander Onbysh 07.12.2019
 */
class ParString(val str: String)
  extends immutable.ParSeq[Char] {
  override def apply(i: Int): Char = str.charAt(i)

  override def length: Int = str.length

  override def seq = new collection.immutable.WrappedString(str)

  override def splitter: SeqSplitter[Char] = new ParStringSplitter(str, 0, length)

  override def newCombiner: ParStringCombiner = new ParStringCombiner

  override def toString = str

}

class ParStringSplitter(private var s: String,
                        private var i: Int,
                        private val limit: Int) extends SeqSplitter[Char] {

  final def hasNext: Boolean = i < limit

  final def next: Char = {
    i += 1
    s.charAt(i - 1)
  }

  override def dup: SeqSplitter[Char] = new ParStringSplitter(s, i, limit)

  override def split: Seq[SeqSplitter[Char]] = {
    if (remaining >= 2) psplit(remaining / 2, remaining - remaining / 2)
    else Seq(this)
  }

  override def psplit(sizes: Int*): Seq[SeqSplitter[Char]] = {
    val chunks = for (size <- sizes) yield {
      val splitLimit = (i + size) min limit
      val ps = new ParStringSplitter(s, i, splitLimit)
      i = splitLimit
      ps
    }
    if (i == limit) chunks
    else chunks :+ new ParStringSplitter(s, i, limit)
  }

  override def remaining: Int = limit - i
}

class ParStringCombiner extends Combiner[Char, ParString] {
  val builders: mutable.MutableList[StringBuilder] = new mutable.MutableList[StringBuilder] += new StringBuilder
  var lastBuilder: StringBuilder = builders.last
  var curSize: Int = 0

  def size: Int = curSize

  def +=(elem: Char): this.type = {
    lastBuilder += elem
    curSize += 1
    this
  }

  def clear: Unit = {
    builders.clear
    builders += new StringBuilder
    lastBuilder = builders.last
    curSize = 0
  }

  def result: ParString = {
    val rsb = new StringBuilder
    for (sb <- builders) rsb.append(sb)
    new ParString(rsb.toString)
  }

  def combine[C <: Char, To >: ParString](other: Combiner[C, To]): ParStringCombiner = if (other eq this) this else {
    val that = other.asInstanceOf[ParStringCombiner]
    curSize += that.curSize
    builders ++= that.builders
    lastBuilder = builders.last
    this
  }
}
