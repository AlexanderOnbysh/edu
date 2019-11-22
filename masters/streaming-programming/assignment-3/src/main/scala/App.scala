import scala.io.Source

/**
 * Created by Alexander Onbysh 21.11.2019
 */

object App extends App {
  def time[R](block: => R, text: String = "Run test", rounds: Int = 10): Unit = {

    var times: Long = 0
    for (_ <- 1 to rounds) {
      val t0 = System.nanoTime()
      val result = block
      times += System.nanoTime() - t0
    }
    println(s"$text: " + times / rounds * 0.000001 + " ms")
  }

  val employees_source = Source.fromResource("employees.csv").getLines
  val departments_source = Source.fromResource("departments.csv").getLines

  val employees = Parser.parseEmployees(employees_source)
  val departments = Parser.parseDepartments(departments_source)

  val nonParallel: Analytics = new NonParallelAnalytics(employees, departments)
  val parallel: Analytics = new NonParallelAnalytics(employees, departments)


  time({
    nonParallel.EmployeesForManagers
  }, text = "NonParallel EmployeesForManagers")
  time({
    parallel.EmployeesForManagers
  }, text = "Parallel EmployeesForManagers")
  println("---")
  time({
    nonParallel.EmployeeWithLowestSalaryInEachDepartment
  }, text = "NonParallel EmployeeWithLowestSalaryInEachDepartment")
  time({
    parallel.EmployeeWithLowestSalaryInEachDepartment
  }, text = "Parallel EmployeeWithLowestSalaryInEachDepartment")
  println("---")
  time({
    nonParallel.EmployeesWithSalaryOfDepartmentHigherThen(10000)
  }, text = "NonParallel EmployeesWithSalaryOfDepartmentHigherThen")
  time({
    parallel.EmployeesWithSalaryOfDepartmentHigherThen(10000)
  }, text = "Parallel EmployeesWithSalaryOfDepartmentHigherThen")
  println("---")
  time({
    nonParallel.EmployeesForManagers
  }, text = "NonParallel EmployeesForManagers")
  time({
    parallel.EmployeesForManagers
  }, text = "Parallel EmployeesForManagers")
}
