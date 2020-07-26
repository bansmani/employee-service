package com.sg.employeeservice

import com.jayway.jsonpath.JsonPath
import com.sg.employeeservice.dto.EmployeeDTO
import com.sg.employeeservice.service.EmployeeService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.data.domain.PageImpl
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import java.net.URI


@SpringBootTest(webEnvironment = RANDOM_PORT)
class RestLayerIntegrationTest(
        @Autowired private val restTemplate: TestRestTemplate
) {

    @MockBean
    lateinit var employeeService: EmployeeService

    @BeforeEach
    fun `before each`() {
        given(employeeService.findEmployee(ArgumentMatchers.anyString())).willReturn(
                TestObjectFactory.getRandomEployee())

        given(employeeService.findAllEmployee()).willReturn(
                PageImpl(TestObjectFactory.getRandomEployees(5)))
    }

    @Test
    fun `endpoint to get all employee should exists`() {
        val response = restTemplate.getForEntity(URI("/employee"), Any::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `endpoint to get one employee should exists`() {
        val response = restTemplate.getForEntity(URI("/employee/fakeid"), Any::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `endpoint to save employee should exists which takes one employee object`() {
        val entity = HttpEntity(TestObjectFactory.getRandomEployee())
        val response = restTemplate.exchange(URI("/employee"), HttpMethod.PUT,
                entity,
                String::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
    }


    @Test
    fun `employee endpoint should return single employee DTO object of given Id`() {
        val response = restTemplate.getForEntity(URI("/employee/fakeid"), EmployeeDTO::class.java)
        assertThat(response?.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertTrue(response?.body is EmployeeDTO)
    }

    @Test
    fun `employee endpoint should return List of Employee DTO objects`() {
        val response = restTemplate.getForObject("/employee", String::class.java)
        val firstName = JsonPath.parse(response).read<String>("$.content[0].firstName")
        assertThat(firstName).isEqualTo("Manish")
    }

    @Test
    fun `employee DTO object should contain all required field`() {
        val response = restTemplate.getForEntity(URI("/employee/fakeid"), EmployeeDTO::class.java)
        Assertions.assertTrue(response.body is EmployeeDTO)
        val employeeDTO = response.body
        val field = employeeDTO?.javaClass?.declaredFields?.map { field -> field.name }
        assertThat(field).containsAll(listOf("firstName", "lastName", "gender", "dob", "department"))
    }

    @Test
    fun `employee list should be pageable`() {
        val response = restTemplate.exchange(URI("/employee"), HttpMethod.GET,
                null, object : ParameterizedTypeReference<RestResponsePage<EmployeeDTO?>?>() {})
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body?.pageable?.isPaged).isTrue()
        assertThat(response.body?.totalElements).isEqualTo(5)
    }



}

