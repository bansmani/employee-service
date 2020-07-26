package com.sg.employeeservice.service

import com.sg.employeeservice.domain.Employee
import com.sg.employeeservice.repository.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service


@Service
class EmployeeService(
        @Autowired private val employeeRepository: EmployeeRepository
) {
    fun findEmployee(employeeId: String): Employee? {
        return employeeRepository.findById(employeeId).orElse(null)
    }

    fun findAllEmployee(page: Int = 0, size: Int = 100): Page<Employee> {
        val pageRequest: Pageable = PageRequest.of(page, size)
        return employeeRepository.findAll(pageRequest)
    }

    fun saveEmployee(employee: Employee) {
        employeeRepository.save(employee)
    }


}
