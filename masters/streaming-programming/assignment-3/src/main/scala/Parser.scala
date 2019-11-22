import java.time.LocalDate
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder}

import scala.util.{Success, Try}

/**
 * Created by Alexander Onbysh 22.11.2019
 */
object Parser {
  val datePattern: DateTimeFormatter = new DateTimeFormatterBuilder()
    .parseCaseInsensitive()
    .appendPattern("dd-MMM-yyyy")
    .toFormatter()

  def parseEmployees(data: Iterator[String]): List[Employee] =
    data.drop(1).flatMap {
      case s"$employee_id,$first_name,$last_name,$email,$_,$hire_date,$job_id,$salary,$_,$manager_id,$department_id" =>
        Some(
          Try(
            Employee(
              employee_id.toInt,
              first_name,
              last_name,
              email,
              LocalDate.parse(hire_date, datePattern),
              job_id,
              salary.toInt,
              Try(manager_id.toInt).getOrElse(-1),
              department_id.trim().toInt)
          )
        )
      case _ => None
    }.collect { case Success(value) => value }
      .toList

  def parseDepartments(data: Iterator[String]): List[Department] =
    data.drop(1).flatMap {
      case s"$department_id,$department_name,$manager_id,$location_id" =>
        Some(
          Try(
            Department(
              department_id.toInt,
              department_name,
              manager_id.toInt,
              location_id.trim().toInt
            )
          )
        )
      case _ => None
    }.collect { case Success(value) => value }
      .toList

}
