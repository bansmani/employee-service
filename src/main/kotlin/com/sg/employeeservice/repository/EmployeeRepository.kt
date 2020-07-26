package com.sg.employeeservice.repository

import com.sg.employeeservice.domain.Employee
import org.springframework.data.repository.CrudRepository

interface EmployeeRepository : CrudRepository<Employee, String>   {

}
