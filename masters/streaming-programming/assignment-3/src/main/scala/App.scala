

/**
 * Created by Alexander Onbysh 21.11.2019
 */

object App extends App {

  val employees = Parser.parseEmployees("employees.csv")
  val departments = Parser.parseDepartments("departments.csv")

  val analytics: Analytics = new NonParallelAnalytics(employees, departments)

  println(analytics.EmployeesForManagers)
  println(analytics.EmployeeWithLowestSalaryInEachDepartment)
  println(analytics.EmployeesWithSalaryOfDepartmentHigherThen(5000))
  println(analytics.EmployeesWhoHiredBeforeManager)
}


