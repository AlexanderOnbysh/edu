/**
 * Created by Alexander Onbysh 21.11.2019
 */
case class Department(
                       departmentId: Int,
                       departmentName: String,
                       managerId: Int,
                       locationId: Int
                     )

object Department {
  def apply(departmentId: Int, departmentName: String, managerId: Int, locationId: Int): Department =
    new Department(departmentId, departmentName, managerId, locationId)
}
