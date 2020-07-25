package com.sg.employeeservice.controller

import com.sg.employeeservice.domain.Gender
import com.sg.employeeservice.dto.EmployeeDTO
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class EmployeeController {

    @GetMapping("/employee")
    fun getAllEmployee(): List<EmployeeDTO> {
        return listOf(EmployeeDTO("", "", "", Gender.MALE, LocalDate.now(), ""))
    }

    @GetMapping("/employee/{empid}")
    fun getEmployee(@PathVariable("empid") empId: String): EmployeeDTO {
        return EmployeeDTO("", "", "", Gender.MALE, LocalDate.now(), "")
    }



}