package com.sg.employeeservice.controller

import com.sg.employeeservice.domain.Employee
import com.sg.employeeservice.domain.Gender
import com.sg.employeeservice.service.EmployeeService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@ExtendWith(SpringExtension::class)
@WebMvcTest(EmployeeController::class, EmployeeService::class)
internal class EmployeeControllerTest(
        @Autowired private val mockMvc: MockMvc

) {

    @MockBean
    lateinit var employeeService: EmployeeService


    @Test
    fun `getEmployee should return employee DTO`() {
        given(employeeService.findEmployee(anyString())).willReturn(
                Employee("EMP001", "Manish", "Bansal",Gender.MALE,
                        LocalDate.of(1990,1,1),"IT"))

        mockMvc.perform(MockMvcRequestBuilders.get("/employee/fakeid"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("empId").value("EMP001"))
                .andExpect(jsonPath("firstName").value("Manish"))
                .andExpect(jsonPath("lastName").value("Bansal"))
                .andExpect(jsonPath("gender").value("MALE"))
                .andExpect(jsonPath("department").value("IT"))
    }




}