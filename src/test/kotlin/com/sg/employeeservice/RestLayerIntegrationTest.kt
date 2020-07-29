package com.sg.employeeservice

import com.jayway.jsonpath.JsonPath
import com.sg.employeeservice.domain.Employee
import com.sg.employeeservice.dto.EmployeeDTO
import com.sg.employeeservice.exceptions.CustomErrorResponse
import com.sg.employeeservice.repository.EmployeeRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.net.URI
import java.time.LocalDate


@SpringBootTest(webEnvironment = RANDOM_PORT)
class RestLayerIntegrationTest(
        @Autowired private val testRestTemplate: TestRestTemplate,
        @Autowired private val employeeRepository: EmployeeRepository
) {


    @BeforeEach
    fun beforeEach() {
        employeeRepository.deleteAllInBatch()
        while (employeeRepository.count() > 0) {
            Thread.sleep(10)
            println("waiting for delete : " + employeeRepository.count())
            employeeRepository.deleteAllInBatch()
        }
    }


    @Test
    fun `endpoint to get all employee should exists`() {
        val response = testRestTemplate.getForEntity(URI("/employee"), Any::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `endpoint to get one employee should exists`() {
        val empId = saveRandomEmployee()
        val response = testRestTemplate.getForEntity(URI("/employee/$empId"), Any::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    }

    @Test
    fun `endpoint to save employee should exists which takes one employee object and return created`() {
        val entity = HttpEntity(TestObjectFactory.getRandomEployee())
        val response = testRestTemplate.exchange(URI("/employee"), HttpMethod.PUT,
                entity,
                String::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
    }


    @Test
    fun `employee endpoint should return single employee DTO object of given Id`() {
        val empId = saveRandomEmployee()
        val response = testRestTemplate.getForEntity(URI("/employee/$empId"), EmployeeDTO::class.java)
        assertThat(response?.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertTrue(response?.body is EmployeeDTO)
    }


    @Test
    fun `employee endpoint should return List of Employee DTO objects`() {
        saveRandomEmployee()
        val response = testRestTemplate.getForObject("/employee", String::class.java)
        val firstName = JsonPath.parse(response).read<String>("$.content[0].firstName")
        assertThat(firstName).isEqualTo("Maria")
    }

    @Test
    fun `employee DTO object should contain all required field`() {
        val empId = saveRandomEmployee()
        val response = testRestTemplate.getForEntity(URI("/employee/$empId"), EmployeeDTO::class.java)
        Assertions.assertTrue(response.body is EmployeeDTO)
        val employeeDTO = response.body
        val field = employeeDTO?.javaClass?.declaredFields?.map { field -> field.name }
        assertThat(field).containsAll(listOf("firstName", "lastName", "gender", "dob", "department"))
    }

    @Test
    fun `employee list should be pageable`() {
        TestObjectFactory.getRandomEployees(5).forEach {
            val entity = HttpEntity(it)
            testRestTemplate.exchange(URI("/employee"), HttpMethod.PUT, entity, String::class.java)
        }
        val response = testRestTemplate.exchange(URI("/employee"), HttpMethod.GET,
                null, object : ParameterizedTypeReference<RestResponsePage<EmployeeDTO?>?>() {})
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body?.pageable?.isPaged).isTrue()
        assertThat(response.body?.totalElements).isEqualTo(5)
    }


    @Test
    fun `employees should be sorted asending default to first name`() {

        val emp1 = TestObjectFactory.getRandomEployee("EMP001", "PQR")
        employeeRepository.save(emp1)
        val emp4 = TestObjectFactory.getRandomEployee("EMP004", "MNO")
        employeeRepository.save(emp4)
        val emp2 = TestObjectFactory.getRandomEployee("EMP002", "XYZ")
        employeeRepository.save(emp2)
        val emp3 = TestObjectFactory.getRandomEployee("EMP003", "ABC")
        employeeRepository.save(emp3)


        val response = testRestTemplate.exchange(URI("/employee"), HttpMethod.GET,
                null, object : ParameterizedTypeReference<RestResponsePage<EmployeeDTO?>?>() {})

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body?.pageable?.isPaged).isTrue()
        assertThat(response.body?.totalElements).isEqualTo(4)
        assertThat(response.body!!.content.map { it!!.firstName }.toString()).isEqualTo("[ABC, MNO, PQR, XYZ]")

    }

    @Test
    fun `should throw exceptoin if employee not found with appropreate infromation`() {
        val response = testRestTemplate.getForEntity(URI("/employee/fakeid"), CustomErrorResponse::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
        assertThat(response.body?.message).isEqualTo("Could not find Employee with id fakeid")

    }


    @Test
    fun `should throw exceptoin when trying to re-register same employee`() {
        val randomEployee = TestObjectFactory.getRandomEployee()
        saveEmployee(randomEployee)
        val response = saveEmployee(randomEployee)
        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }


    @Test
    fun `validate date of birth can not be future date`(){
        val randomEployee = TestObjectFactory.getRandomEployee(dob = LocalDate.now().plusDays(1))
        val response  = saveEmployee(randomEployee, CustomErrorResponse::class.java)
        println(response)
        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(response.body?.message).isEqualTo("Date of birth can not future date")
    }

    private fun saveRandomEmployee(): String {
        val randomEployee = TestObjectFactory.getRandomEployee()
        saveEmployee(randomEployee)
        return randomEployee.empId

    }

    private fun saveEmployee(randomEployee: Employee): ResponseEntity<String> {
        val entity = HttpEntity(randomEployee)
        return testRestTemplate.exchange(URI("/employee"), HttpMethod.PUT, entity, String::class.java)
    }

    private fun <T> saveEmployee(randomEployee: Employee, classz: Class<T>): ResponseEntity<T> {
        val entity = HttpEntity(randomEployee)
        return testRestTemplate.exchange(URI("/employee"), HttpMethod.PUT, entity, classz)
    }


}


