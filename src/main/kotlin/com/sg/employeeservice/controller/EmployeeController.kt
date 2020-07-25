package com.sg.employeeservice.controller

import com.sg.employeeservice.domain.Gender
import com.sg.employeeservice.dto.EmployeeDTO
import com.sg.employeeservice.service.EmployeeService
import org.springframework.beans.BeanUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class EmployeeController(
        @Autowired private val employeeService: EmployeeService
) {

    @GetMapping("/employee")
    fun getAllEmployee(): List<EmployeeDTO> {
        return listOf(EmployeeDTO("", "", "", Gender.MALE, LocalDate.now(), ""))
    }

    @GetMapping("/employee/{empid}")
    fun getEmployee(@PathVariable("empid") empId: String): EmployeeDTO? {
         return employeeService.findEmployee(empId)?.toDTO()
    }

}