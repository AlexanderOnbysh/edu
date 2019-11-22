import scala.io.Source

/**
 * Created by Alexander Onbysh 21.11.2019
 */

object App extends App {
  val employees_source = Source.fromResource("employees.csv").getLines
  val departments_source = Source.fromResource("departments.csv").getLines

  val employees = Parser.parseEmployees(employees_source)
  val departments = Parser.parseDepartments(departments_source)

  val analytics: Analytics = new NonParallelAnalytics(employees, departments)

  println(employees)
  println(analytics.EmployeesForManagers)
  println(analytics.EmployeeWithLowestSalaryInEachDepartment)
  println(analytics.EmployeesWithSalaryOfDepartmentHigherThen(5000))
  println(analytics.EmployeesWhoHiredBeforeManager)
}
