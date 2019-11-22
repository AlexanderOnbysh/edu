import java.time.LocalDate
import java.time.format.DateTimeFormatterBuilder

import scala.io.Source
import scala.util.Try

/**
 * Created by Alexander Onbysh 22.11.2019
 */
object Parser {
  val datePattern = new DateTimeFormatterBuilder()
    .parseCaseInsensitive()
    .appendPattern("dd-MMM-yyyy")
    .toFormatter();

  def parseEmployees(path: String): List[Employee] =
    Source.fromResource(path).getLines().drop(1).map {
      case s"$employee_id,$first_name,$last_name,$email,$_,$hire_date,$job_id,$salary,$_,$manager_id,$department_id" =>
        Right(
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
      case line => Left(s"Cannot read '$line'")
    }.filter(_.isRight).map(_.right.get).toList

  def parseDepartments(path: String): List[Department] =
    Source.fromResource(path).getLines().drop(1).map {
      case s"$department_id,$department_name,$manager_id,$location_id" => Right(
        Department(
          department_id.toInt,
          department_name,
          manager_id.toInt,
          location_id.trim().toInt
        )
      )
      case line => Left(s"Cannot read '$line'")
    }.filter(_.isRight).map(_.right.get).toList

}
