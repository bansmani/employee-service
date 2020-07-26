package com.sg.employeeservice.repository

import com.sg.employeeservice.domain.Employee
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface EmployeeRepository : CrudRepository<Employee, String>   {

}
