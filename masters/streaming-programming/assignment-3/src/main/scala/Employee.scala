import java.time.LocalDate

///**
// * Created by Alexander Onbysh 21.11.2019
// */
case class Employee(
                     id: Int,
                     firstName: String,
                     lastName: String,
                     email: String,
                     hireDate: LocalDate,
                     jobId: String,
                     salary: Int,
                     managerId: Int,
                     departmentId: Int
                   )
