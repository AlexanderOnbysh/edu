/**
 * Created by Alexander Onbysh 23.12.2019
 */


object StringImprovements extends App {

  implicit class StringImprovements(val s: String) {

    def -(other: String): String = (s.toInt - other.toInt).toString

    def *(other: String): String = (s.toInt * other.toInt).toString

    def /(other: String): String = (s.toInt / other.toInt).toString
  }


  assert("6" * "2" - "3" / "9" == "12")
  assert("6" * "3" - "3" / "9" == "18")
  assert("6" * "3" - "9" / "9" == "17")
  assert("6" * "3" - "9" / "9" - "-10" == "27")
}
