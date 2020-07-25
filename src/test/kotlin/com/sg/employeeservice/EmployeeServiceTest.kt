package com.sg.employeeservice

import com.sg.employeeservice.service.EmployeeService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired


class EmployeeServiceTest(
        @Autowired val employeeService: EmployeeService
) {


    @Test
    fun `assert that employee service gives employee object`() {

      //  employeeService.getEmployee("emp1")
    }

}