import java.time.LocalDate

import org.scalatest._

class EmployeeParserTest extends FlatSpec with Matchers {

  it should "return empty list" in {
    val header = "employee_id,first_name,last_name,email,phone_number,hire_date,job_id,salary,commission_pct,manager_id,department_id\n"
    assert(Parser.parseEmployees(header.linesIterator).isEmpty)
  }

  it should "return employee" in {
    val header =
      "employee_id,first_name,last_name,email,phone_number,hire_date,job_id,salary,commission_pct,manager_id,department_id\n" +
        "101,Neena,Kochhar,NKOCHHAR,515.123.4568,21-SEP-1989,AD_VP,17000,NULL,100,90"
    val result = Parser.parseEmployees(header.linesIterator)

    assert(result.size == 1)
    val date = LocalDate.now().withDayOfMonth(21).withMonth(9).withYear(1989)
    assert(result.head == Employee(101, "Neena", "Kochhar", "NKOCHHAR", date, "AD_VP", 17000, 100, 90))
  }

  it should "return two employee" in {
    val header =
      "employee_id,first_name,last_name,email,phone_number,hire_date,job_id,salary,commission_pct,manager_id,department_id\n" +
        "101,Neena,Kochhar,NKOCHHAR,515.123.4568,21-SEP-1989,AD_VP,17000,NULL,100,90\n" +
        "104,Bruce,Ernst,BERNST,590.423.4568,21-MAY-1991,IT_PROG,6000,NULL,NULL,60"

    val date1 = LocalDate.now().withDayOfMonth(21).withMonth(9).withYear(1989)
    val date2 = LocalDate.now().withDayOfMonth(21).withMonth(5).withYear(1991)
    val expected = List(
      Employee(101, "Neena", "Kochhar", "NKOCHHAR", date1, "AD_VP", 17000, 100, 90),
      Employee(104, "Bruce", "Ernst", "BERNST", date2, "IT_PROG", 6000, -1, 60),
    )
    val result = Parser.parseEmployees(header.linesIterator)

    assert(result.size == 2)
    assert(result == expected)
  }

  it should "return empty list; invalid data" in {
    val header =
      "employee_id,first_name,last_name,email,phone_number,hire_date,job_id,salary,commission_pct,manager_id,department_id\n" +
        "1a1,Neena,Kochhar,NKOCHHAR,515.123.4568,21-SEP-1989,AD_VP,17000,NULL,100,90"
    assert(Parser.parseEmployees(header.linesIterator).isEmpty)
  }

}