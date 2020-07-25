package com.sg.employeeservice

import com.sg.employeeservice.domain.Employee
import com.sg.employeeservice.domain.Gender
import java.time.LocalDate
import kotlin.random.Random


/***
 * purpose of this class is to provide handy methods for creating test objects
 * since we can mock/spy some objects due to class is by-default final in kotlin
 * and also parameters types are set as non null
 */
object TestObjectFactory {

    fun getRandomEployee() =
        Employee("EMP" + Random(4).nextInt(), "Manish", "Bansal", Gender.MALE,
                LocalDate.of(1990, 1, 1), "IT")


}