package com.sg.employeeservice

import com.sg.employeeservice.domain.Employee
import com.sg.employeeservice.domain.Gender
import java.time.LocalDate
import kotlin.math.absoluteValue
import kotlin.random.Random


/***
 * purpose of this class is to provide handy methods for creating test objects
 * since we can mock/spy some objects due to class is by-default final in kotlin
 * and also parameters types are set as non null
 */
object TestObjectFactory {

    //TODO: change random method
    fun getRandomEployee(empId: String = "EMP" + Random(4).nextInt(), firstName : String = "Maria") =
            Employee(empId, firstName, "Jane", Gender.FEMALE,
                    LocalDate.of(1990, 1, 1), "IT")

    fun getRandomEployees(cnt: Int) : List<Employee> {
        val random = Random(4)
        return (1..cnt).map {
            Employee("EMP" + random.nextInt().absoluteValue, "Manish", "Bansal", Gender.MALE,
                    LocalDate.of(1990, 1, 1), "IT")
        }
    }
}