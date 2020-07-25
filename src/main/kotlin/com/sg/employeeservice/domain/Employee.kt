package com.sg.employeeservice.domain

import com.sg.employeeservice.dto.EmployeeDTO
import java.time.LocalDate


class Employee(val empId: String,
               val firstName: String,
               val lastName: String,
               val gender: Gender,
               val dob: LocalDate,
               val department: String) {
    fun toDTO() = EmployeeDTO(empId, firstName, lastName, gender, dob, department)
}
