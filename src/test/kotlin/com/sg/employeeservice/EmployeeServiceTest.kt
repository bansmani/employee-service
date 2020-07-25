package com.sg.employeeservice

import com.sg.employeeservice.service.EmployeeService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.junit.jupiter.SpringExtension



@ExtendWith(SpringExtension::class)
@WebMvcTest(EmployeeService::class)
class EmployeeServiceTest(
        @Autowired val employeeService: EmployeeService
) {


    @Test
    fun `assert that employee service gives employee object`() {

      //  employeeService.getEmployee("emp1")
    }

}