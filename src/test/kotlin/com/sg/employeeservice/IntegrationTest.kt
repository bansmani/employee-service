package com.sg.employeeservice

import com.sg.employeeservice.domain.Employee
import com.sg.employeeservice.domain.Gender
import com.sg.employeeservice.dto.EmployeeDTO
import com.sg.employeeservice.service.EmployeeService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.net.URI
import java.time.LocalDate


@SpringBootTest(webEnvironment = RANDOM_PORT)
class IntegrationTest(
        @Autowired private val restTemplate: TestRestTemplate
) {

    @MockBean
    lateinit var employeeService: EmployeeService

    @BeforeEach
    fun `before each`() {
        given(employeeService.findEmployee(ArgumentMatchers.anyString())).willReturn(
                Employee("EMP001", "Manish", "Bansal", Gender.MALE,
                        LocalDate.of(1990, 1, 1), "IT"))
    }

    @Test
    fun `employee endpoint should exists`() {
        val response = restTemplate.getForEntity(URI("/employee"), Any::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `employee endpoint should return List of Employee DTO objects`() {
        val response = restTemplate.getList<List<EmployeeDTO>>("/employee")
        Assertions.assertTrue(response?.body is List<EmployeeDTO>)
    }

    @Test
    fun `employee endpoint should return single employee DTO object of given Id`() {
        val response = restTemplate.getForEntity(URI("/employee/fakeid"), EmployeeDTO::class.java)
        assertThat(response?.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertTrue(response?.body is EmployeeDTO)
    }

    @Test
    fun `employee DTO object should contain all required field`() {
        val response = restTemplate.getForEntity(URI("/employee/fakeid"), EmployeeDTO::class.java)
        Assertions.assertTrue(response.body is EmployeeDTO)
        val employeeDTO = response.body
        val field = employeeDTO?.javaClass?.declaredFields?.map { field -> field.name }
        assertThat(field).containsAll(listOf("firstName", "lastName", "gender", "dob", "department"))
    }


    fun <T> TestRestTemplate.getList(url: String): ResponseEntity<T>? {
        return exchange(url, HttpMethod.GET, null, object : ParameterizedTypeReference<T>() {})
    }
}


