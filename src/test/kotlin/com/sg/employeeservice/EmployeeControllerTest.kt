package com.sg.employeeservice

import com.fasterxml.jackson.databind.ObjectMapper
import com.sg.employeeservice.controller.EmployeeController
import com.sg.employeeservice.domain.Employee
import com.sg.employeeservice.domain.Gender
import com.sg.employeeservice.exceptions.EmployeeNotFoundException
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
import org.springframework.data.domain.PageImpl
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
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
               PageImpl( listOf(Employee("EMP001", "Manish", "Bansal", Gender.MALE,
                        LocalDate.of(1990, 1, 1), "IT"),
                        Employee("EMP002", "John", "Rose", Gender.OTHERS,
                                LocalDate.of(1990, 1, 1), "HRD"))))

        mockMvc.perform(get("/employee"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.content[0].empId").value("EMP001"))
                .andExpect(jsonPath("$.content[1].empId").value("EMP002"))
                .andExpect(jsonPath("$.content[0].firstName").value("Manish"))
                .andExpect(jsonPath("$.content[0].lastName").value("Bansal"))
                .andExpect(jsonPath("$.content[0].gender").value("MALE"))
                .andExpect(jsonPath("$.content[0].department").value("IT"))
    }


    @Test
    fun `save employee should take employee object and call service to save employee`() {
        mockMvc.perform(put("/employee").contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(TestObjectFactory.getRandomEployee())))
        verify(employeeService, Mockito.times(1)).saveEmployee(any(Employee::class.java))
    }


    @Test
    fun `user should get meaningfull friendly exception`() {
        given(employeeService.findEmployee(anyString())).willThrow(
                EmployeeNotFoundException("Could not find Employee with id E001"))

        mockMvc.perform(get("/employee/fakeid"))
                .andExpect(status().isNotFound)
                .andExpect(jsonPath("message").value("Could not find Employee with id E001"))
    }



}