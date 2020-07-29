package com.sg.employeeservice.service

import com.sg.employeeservice.domain.Employee
import com.sg.employeeservice.exceptions.EmployeeAlreadyExistsException
import com.sg.employeeservice.exceptions.EmployeeNotFoundException
import com.sg.employeeservice.repository.EmployeeRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.LocalDate


@Service
class EmployeeService(
        @Autowired private val employeeRepository: EmployeeRepository) {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    fun findEmployee(employeeId: String): Employee? {
        return employeeRepository.findById(employeeId).orElseThrow {
            EmployeeNotFoundException("Could not find Employee with id $employeeId")
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
        if (employee.dob.isAfter(LocalDate.now()))
            throw IllegalArgumentException("Date of birth can not future date")
        try {
            employeeRepository.save(employee)
        } catch (e: DataIntegrityViolationException){
            throw EmployeeAlreadyExistsException("Please change employee id and retry")
        }
        catch (e: Exception) {
            logger.error("Exception Saving Employee", e)
            throw e
        }
    }


}
