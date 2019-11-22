/**
 * Created by Alexander Onbysh 21.11.2019
 */

class NonParallelAnalytics(employees: Iterable[Employee],
                           departments: List[Department]) extends Analytics {
  override def EmployeesForManagers: List[(Employee, Int)] =
    (for (
      (key, value) <- this.employees.groupBy(_.managerId)
      if this.employees.exists(_.id == key)
    ) yield (this.employees.find(_.id == key).head, value.size)
      ).toList

  override def EmployeeWithLowestSalaryInEachDepartment: List[(Department, Employee)] =
    (for ((key, value) <- this.employees.groupBy(_.departmentId))
      yield (this.departments.find(_.departmentId == key).head, value.minBy(_.salary))
      ).toList

  override def EmployeesWithSalaryOfDepartmentHigherThen(salary: Int): List[Employee] =
    (
      for (
        value <- this.employees.groupBy(_.departmentId).values
        if (for (v <- value) yield v.salary).sum > salary
      ) yield value
      )
      .flatten
      .toList

  override def EmployeesWhoHiredBeforeManager: List[Employee] =
    (for ((key, value) <-
            for ((key, value) <- for ((key, value) <- this.employees.groupBy(_.departmentId))
              yield (
                (for (department <- this.departments if department.departmentId == key) yield department).head,
                value
              )
                 if this.employees.exists(_.id == key.managerId)
                 ) yield
              (
                (for (e <- this.employees if e.id == key.managerId) yield e).head,
                value
              )
          )
      yield for (v <- value if v.hireDate.isBefore(key.hireDate)) yield v
      ).flatten
      .toList
}

