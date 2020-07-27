package com.sg.employeeservice.service

import com.sg.employeeservice.domain.Employee
import com.sg.employeeservice.exceptions.EmployeeAlreadyExistsException
import com.sg.employeeservice.exceptions.ResourceNotFoundException
import com.sg.employeeservice.repository.EmployeeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


@Service
class EmployeeService(
        @Autowired private val employeeRepository: EmployeeRepository)
{

    fun findEmployee(employeeId: String): Employee? {
        return employeeRepository.findById(employeeId).orElseThrow {
            ResourceNotFoundException("Could not find employee with id $employeeId")
        }
    }

    fun findAllEmployee(page: Int = 0,
                        size: Int = 100,
                        sortBy: String = "firstName",
                        sortDirection: Sort.Direction = Sort.Direction.ASC): Page<Employee> {
        val pageRequest: Pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, sortBy))
        return employeeRepository.findAll(pageRequest)
    }

    fun saveEmployee(employee: Employee) {
        try {
            employeeRepository.save(employee)
        } catch (e: Exception) {
            throw EmployeeAlreadyExistsException("Please change employee id and retry")
        }
    }


}
