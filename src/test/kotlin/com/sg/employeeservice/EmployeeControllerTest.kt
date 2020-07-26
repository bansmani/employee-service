package com.sg.employeeservice

import com.fasterxml.jackson.databind.ObjectMapper
import com.sg.employeeservice.controller.EmployeeController
import com.sg.employeeservice.domain.Employee
import com.sg.employeeservice.domain.Gender
import com.sg.employeeservice.service.EmployeeService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDate

@ExtendWith(SpringExtension::class)
@WebMvcTest(EmployeeController::class, EmployeeService::class)
internal class EmployeeControllerTest(
        @Autowired private val mockMvc: MockMvc,
        @Autowired val objectMapper: ObjectMapper


) {


    @MockBean
    lateinit var employeeService: EmployeeService


    @Test
    fun `getEmployee should return employee DTO`() {
        given(employeeService.findEmployee(anyString())).willReturn(
                Employee("EMP001", "Manish", "Bansal", Gender.MALE,
                        LocalDate.of(1990, 1, 1), "IT"))

        mockMvc.perform(get("/employee/fakeid"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("empId").value("EMP001"))
                .andExpect(jsonPath("firstName").value("Manish"))
                .andExpect(jsonPath("lastName").value("Bansal"))
                .andExpect(jsonPath("gender").value("MALE"))
                .andExpect(jsonPath("department").value("IT"))
    }


    @Test
    fun `getAllEmployee should return list of employees DTO`() {
        given(employeeService.findAllEmployee()).willReturn(
                listOf(Employee("EMP001", "Manish", "Bansal", Gender.MALE,
                        LocalDate.of(1990, 1, 1), "IT"),
                        Employee("EMP002", "John", "Rose", Gender.OTHERS,
                                LocalDate.of(1990, 1, 1), "HRD")))

        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$[0].empId").value("EMP001"))
                .andExpect(jsonPath("$[1].empId").value("EMP002"))
                .andExpect(jsonPath("$[0].firstName").value("Manish"))
                .andExpect(jsonPath("$[0].lastName").value("Bansal"))
                .andExpect(jsonPath("$[0].gender").value("MALE"))
                .andExpect(jsonPath("$[0].department").value("IT"))
    }


    @Test
    fun `save employee should take employee object and call service to save employee`() {
        mockMvc.perform(post("/employee").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TestObjectFactory.getRandomEployee())))
        verify(employeeService, Mockito.times(1)).saveEmployee(any(Employee::class.java))
    }


}