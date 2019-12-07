/**
 * Created by Alexander Onbysh 07.12.2019
 */

import Utils._

object Application extends App {
  val text = "Hello world sample text file" * 1000000
  val parString = new ParString(text)

  time(
    {
      text.foldLeft(0) { (n, c) => if (c == 'a') n + 1 else n }
    },
    "String: calculate number of 'a'",
  )
  time(
    {
      parString.aggregate(0)(
        (n, c) => if (c == 'a') n + 1 else n,
        _ + _
      )
    },
    "ParString: calculate number of 'a'",
  )
  time(
    {
      text.filter(c => c == 'a')
    },
    "String: filter all 'a' letters",
  )
  time(
    {
      parString.filter(c => c == 'a')
    },
    "ParString: filter all 'a' letters",
  )
}
