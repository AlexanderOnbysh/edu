import java.time.LocalDate

import org.scalatest._

class AnalyticsTest extends FlatSpec with Matchers {

  //  fixture builder for Employee class
  def EB(id: Int, managerId: Int = 0, departmentId: Int = 0,
         salary: Int = 0, hireDate: LocalDate = LocalDate.now()): Employee =
    Employee(id, "", "", "", hireDate, "", salary, managerId, departmentId)

  //  fixture builder for Department class
  def DB(id: Int, managerId: Int = 0): Department = Department(id, "", managerId, 0)

  it should "empty list" in {
    assert(ParallelAnalytics(List(), List()).EmployeesForManagers.isEmpty)
    assert(ParallelAnalytics(List(), List()).EmployeesWhoHiredBeforeManager.isEmpty)
    assert(ParallelAnalytics(List(), List()).EmployeesWithSalaryOfDepartmentHigherThen(0).isEmpty)
    assert(ParallelAnalytics(List(), List()).EmployeeWithLowestSalaryInEachDepartment.isEmpty)
  }

  it should "EmployeesForManagers: three managers" in {

    val employees = List(
      EB(0, -1),
      EB(1, 0),
      EB(2, 0),
      EB(3, 2),
      EB(4, 3),
    )

    val expected = List(
      (EB(0, -1), 2),
      (EB(2, 0), 1),
      (EB(3, 2), 1),
    ).sortBy(_._1.id)

    var result = ParallelAnalytics(employees, List()).EmployeesForManagers.sortBy(_._1.id)
    assert(result == expected)

    result = NonParallelAnalytics(employees, List()).EmployeesForManagers.sortBy(_._1.id)
    assert(result == expected)
  }

  it should "EmployeeWithLowestSalaryInEachDepartment: three departments" in {
    val departments = List(DB(1), DB(2), DB(3))
    val employees = List(
      EB(0, salary = 10, departmentId = 1),
      EB(1, salary = 20, departmentId = 1),
      EB(2, salary = 20, departmentId = 1),
      EB(3, salary = 1, departmentId = 2),
      EB(4, salary = 100, departmentId = 3),
    )

    val expected = List(
      (DB(1), EB(0, salary = 10, departmentId = 1)),
      (DB(2), EB(3, salary = 1, departmentId = 2)),
      (DB(3), EB(4, salary = 100, departmentId = 3)),
    ).sortBy(_._1.departmentId)

    var result = ParallelAnalytics(employees, departments).EmployeeWithLowestSalaryInEachDepartment
      .sortBy(_._1.departmentId)
    assert(expected == result)

    result = NonParallelAnalytics(employees, departments).EmployeeWithLowestSalaryInEachDepartment
      .sortBy(_._1.departmentId)
    assert(expected == result)
  }

  it should "EmployeesWithSalaryOfDepartmentHigherThen: two departments" in {
    val departments = List(DB(1), DB(2))
    val employees = List(
      EB(0, salary = 10, departmentId = 1),
      EB(1, salary = 20, departmentId = 1),
      EB(2, salary = 20, departmentId = 1),
      EB(3, salary = 1, departmentId = 2),
      EB(4, salary = 2, departmentId = 2),
    )

    val expected = List(
      EB(0, salary = 10, departmentId = 1),
      EB(1, salary = 20, departmentId = 1),
      EB(2, salary = 20, departmentId = 1),
    ).sortBy(_.id)

    var result = ParallelAnalytics(employees, departments).EmployeesWithSalaryOfDepartmentHigherThen(100)
      .sortBy(_.id)
    assert(result.isEmpty)
    result = NonParallelAnalytics(employees, departments).EmployeesWithSalaryOfDepartmentHigherThen(100)
      .sortBy(_.id)
    assert(result.isEmpty)

    result = ParallelAnalytics(employees, departments).EmployeesWithSalaryOfDepartmentHigherThen(10)
      .sortBy(_.id)
    assert(expected == result)
    result = NonParallelAnalytics(employees, departments).EmployeesWithSalaryOfDepartmentHigherThen(10)
      .sortBy(_.id)
    assert(expected == result)

    result = ParallelAnalytics(employees, departments).EmployeesWithSalaryOfDepartmentHigherThen(0)
      .sortBy(_.id)
    assert(result == employees)
    result = NonParallelAnalytics(employees, departments).EmployeesWithSalaryOfDepartmentHigherThen(0)
      .sortBy(_.id)
    assert(result == employees)
  }

  it should "EmployeesWhoHiredBeforeManager: two employees" in {
    val departments = List(
      DB(1, managerId = 0),
      DB(2, managerId = 1),
      DB(3, managerId = 2)
    )
    val employees = List(
      EB(0, departmentId = 1, hireDate = LocalDate.now().plusDays(1)),
      EB(1, departmentId = 2, hireDate = LocalDate.now().plusDays(1)),
      EB(2, departmentId = 3, hireDate = LocalDate.now().plusDays(1)),
      EB(3, departmentId = 1, hireDate = LocalDate.now()),
      EB(4, departmentId = 2, hireDate = LocalDate.now().plusDays(2)),
      EB(5, departmentId = 3, hireDate = LocalDate.now().plusMonths(1)),
      EB(6, departmentId = 3, hireDate = LocalDate.now()),
    )

    val expected = List(
      EB(3, departmentId = 1, hireDate = LocalDate.now()),
      EB(6, departmentId = 3, hireDate = LocalDate.now()),
    ).sortBy(_.id)

    var result = ParallelAnalytics(employees, departments).EmployeesWhoHiredBeforeManager
      .sortBy(_.id)
    assert(result == expected)
    result = NonParallelAnalytics(employees, departments).EmployeesWhoHiredBeforeManager
      .sortBy(_.id)
    assert(result == expected)

  }
}


