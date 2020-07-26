package com.sg.employeeservice.service

import com.sg.employeeservice.domain.Employee
import com.sg.employeeservice.repository.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
class EmployeeService(
        @Autowired private val employeeRepository: EmployeeRepository
) {
    fun findEmployee(employeeId: String): Employee? {
        return employeeRepository.findById(employeeId).orElse(null)
    }

    fun findAllEmployee(): List<Employee> {
        return employeeRepository.findAll().toList()
    }

    fun saveEmployee(employee: Employee) {

    }


}
