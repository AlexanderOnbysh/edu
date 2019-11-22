

/**
 * Created by Alexander Onbysh 21.11.2019
 */
class ParallelAnalytics(employees: Iterable[Employee],
                        departments: Iterable[Department]
                       ) extends Analytics {

  override def EmployeesForManagers: List[(Employee, Int)] =
    this.employees
      .groupBy(_.managerId)
      .filter(e => this.employees.exists(_.id == e._1))
      .map { case (key, value) => (this.employees.find(_.id == key).head, value.size) }
      .toList

  override def EmployeeWithLowestSalaryInEachDepartment: List[(Department, Employee)] =
    this.employees
      .groupBy(_.departmentId)
      //      replace with find
      .map { case (key, value) => (this.departments.find(_.departmentId == key).head, value.minBy(_.salary)) }
      .toList

  override def EmployeesWithSalaryOfDepartmentHigherThen(salary: Int): List[Employee] =
    this.employees
      .groupBy(_.departmentId)
      .values
      .filter(_.map(_.salary).sum > salary)
      .flatten
      .toList

  override def EmployeesWhoHiredBeforeManager: List[Employee] =
    this.employees
      .groupBy(_.departmentId)
      //      [Int, Iterable[Employee]]
      .map { case (key, value) => (this.departments.filter(_.departmentId == key).head, value) }
      //      [Department, Iterable[Employee]]
      .filter(e => this.employees.exists(_.id == e._1.managerId))
      //      [Department, Iterable[Employee]]
      .map { case (key, value) => (this.employees.find(_.id == key.managerId).head, value) }
      //      [Employee, Iterable[Employee]]
      .map { case (key, value) => (key, value.filter(_.hireDate.isBefore(key.hireDate))) }
      //      [Employee, Iterable[Employee]]
      .values
      .flatten
      .toList
}