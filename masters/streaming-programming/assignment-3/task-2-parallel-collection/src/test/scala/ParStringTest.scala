/**
 * Created by Alexander Onbysh 07.12.2019
 */

import org.scalatest._

class ParStringTest extends FlatSpec with Matchers {

  val text: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod " +
    "tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
    "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."

  it should "be equal to string" in {
    new ParString(text).toString() should be(text)
  }

  it should "not be equal" in {
    new ParString("some text").toString() shouldNot be(text)
  }

  it should "filter as String" in {
    new ParString(text).filter(c => c == 'a').toString should be(text.filter(c => c == 'a'))
  }
}

