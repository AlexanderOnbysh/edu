import scala.collection.parallel.CollectionConverters._

/**
 * Created by Alexander Onbysh 21.11.2019
 */
class ParallelAnalytics(employees: Iterable[Employee],
                        departments: Iterable[Department]
                       ) extends Analytics {

  override def EmployeesForManagers: List[(Employee, Int)] =
    this.employees
      .par
      .groupBy(_.managerId)
      .filter(e => this.employees.exists(_.id == e._1))
      .map { case (key, value) => (this.employees.find(_.id == key).head, value.size) }
      .toList

  override def EmployeeWithLowestSalaryInEachDepartment: List[(Department, Employee)] =
    this.employees
      .par
      .groupBy(_.departmentId)
      .map { case (key, value) => (this.departments.find(_.departmentId == key).head, value.minBy(_.salary)) }
      .toList

  override def EmployeesWithSalaryOfDepartmentHigherThen(salary: Int): List[Employee] =
    this.employees
      .par
      .toList
      .groupBy(_.departmentId)
      .values
      .filter(_.map(_.salary).sum > salary)
      .flatten
      .toList

  override def EmployeesWhoHiredBeforeManager: List[Employee] =
    this.employees
      .par
      .groupBy(_.departmentId)
      .map { case (key, value) => (this.departments.filter(_.departmentId == key).head, value) }
      .filter(e => this.employees.exists(_.id == e._1.managerId))
      .map { case (key, value) => (this.employees.find(_.id == key.managerId).head, value) }
      .map { case (key, value) => (key, value.filter(_.hireDate.isBefore(key.hireDate))) }
      .values
      .flatten
      .toList
}

object ParallelAnalytics {
  def apply(employees: Iterable[Employee], departments: Iterable[Department]): ParallelAnalytics =
    new ParallelAnalytics(employees, departments)
}