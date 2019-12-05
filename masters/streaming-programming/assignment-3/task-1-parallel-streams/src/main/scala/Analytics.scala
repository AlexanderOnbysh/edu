/**
 * Created by Alexander Onbysh 21.11.2019
 */
trait Analytics {

  def EmployeesForManagers: List[(Employee, Int)]

  def EmployeeWithLowestSalaryInEachDepartment: List[(Department, Employee)]

  def EmployeesWithSalaryOfDepartmentHigherThen(salary: Int): List[Employee]

  def EmployeesWhoHiredBeforeManager: List[Employee]
}
