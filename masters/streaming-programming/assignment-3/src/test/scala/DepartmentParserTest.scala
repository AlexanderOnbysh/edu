import org.scalatest._

class DepartmentParserTest extends FlatSpec with Matchers {

  it should "return empty list" in {
    val header = "department_id,department_name,manager_id,location_id\n"
    assert(Parser.parseDepartments(header.linesIterator).isEmpty)
  }

  it should "return department" in {
    val header = "department_id,department_name,manager_id,location_id\n" +
      "90,Administration,200,1700\n"
    val result = Parser.parseDepartments(header.linesIterator)

    assert(result.size == 1)
    assert(result.head == Department(90, "Administration", 200, 1700))
  }

  it should "return two departments" in {
    val header = "department_id,department_name,manager_id,location_id\n" +
      "90,Administration,200,1700\n" +
      "40,Human Resources,203,2400"

    val expected = List(
      Department(90, "Administration", 200, 1700),
      Department(40, "Human Resources", 203, 2400),
    )
    val result = Parser.parseDepartments(header.linesIterator)

    assert(result.size == 2)
    assert(result == expected)
  }

  it should "return empty list; invalid data" in {
    val header = "department_id,department_name,manager_id,location_id\n" +
      "9a0,Administration,200,1700\n"
    val result = Parser.parseDepartments(header.linesIterator)

    assert(result.isEmpty)
  }

}