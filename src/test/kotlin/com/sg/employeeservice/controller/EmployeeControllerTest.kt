package com.sg.employeeservice.controller

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(EmployeeController::class)
internal class EmployeeControllerTest(
        @Autowired private val mockMvc: MockMvc
) {


    @Test
    fun `getEmployee should return employee DTO`() {
        
        mockMvc.perform(MockMvcRequestBuilders.get("/employee/fakeid"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("firstName").value("Manish"))


    }


}