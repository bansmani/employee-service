package com.sg.employeeservice.dto

import com.sg.employeeservice.domain.Gender
import java.time.LocalDate



data class EmployeeDTO(val empId: String,
                       val firstName: String,
                       val lastName: String,
                       val gender: Gender,
                       val dob: LocalDate,
                       val department: String)
