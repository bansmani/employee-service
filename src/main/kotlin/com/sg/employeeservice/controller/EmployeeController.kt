package com.sg.employeeservice.controller

import com.sg.employeeservice.domain.Employee
import com.sg.employeeservice.dto.EmployeeDTO
import com.sg.employeeservice.service.EmployeeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Suppress("unused")
@RestController
class EmployeeController(
        @Autowired val employeeService: EmployeeService
) {

    @GetMapping("/health")
    fun health(): String {
        return "OK"
    }

    @GetMapping("/employee")
    fun getAllEmployee(@RequestParam(defaultValue = "0") page: Int = 0,
                       @RequestParam(defaultValue = "100") size: Int = 100,
                       @RequestParam(defaultValue = "firstName") sortBy: String = "firstName",
                       @RequestParam(defaultValue = "ASC") sortDirection: Sort.Direction = Sort.Direction.ASC): Page<EmployeeDTO> {
        return employeeService.findAllEmployee(page, size, sortBy, sortDirection).map { it.toDTO() }
    }

    @GetMapping("/employee/{empid}")
    fun getEmployee(@PathVariable("empid") empId: String): EmployeeDTO? {
        return employeeService.findEmployee(empId)?.toDTO()
    }

    @PutMapping("/employee")
    @ResponseStatus(HttpStatus.CREATED)
    fun saveEmployee(@RequestBody employee: Employee) {
        employeeService.saveEmployee(employee)
    }


}