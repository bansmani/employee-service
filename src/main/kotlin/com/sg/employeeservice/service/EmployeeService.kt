package com.sg.employeeservice.service

import com.sg.employeeservice.domain.Employee
import org.springframework.stereotype.Service


@Service
class EmployeeService {
    fun findEmployee(employeeId: String): Employee? {
        return null
    }

    fun findAllEmployee() :  List<Employee> {
        return emptyList()
    }

    fun saveEmployee(employee: Employee) {

    }


}
