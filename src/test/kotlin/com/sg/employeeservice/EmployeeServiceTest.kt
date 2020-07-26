package com.sg.employeeservice

import com.sg.employeeservice.domain.Employee
import com.sg.employeeservice.repository.EmployeeRepository
import com.sg.employeeservice.service.EmployeeService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.util.*


@WebMvcTest(EmployeeService::class)
class EmployeeServiceTest {

    @MockBean
    lateinit var employeeRepository: EmployeeRepository
    @Autowired
    lateinit var employeeService: EmployeeService


    @Test
    fun `findEmployee for given empid return employee with that id`() {
        given(employeeRepository.findById("EMP001"))
                .willReturn(Optional.of(TestObjectFactory.getRandomEployee("EMP001")))

        val employee = employeeService.findEmployee("EMP001")
        assertThat(employee?.empId).isEqualTo("EMP001")
    }


    @Test
    fun `findAllEmployee returns list of employee`() {
        given(employeeRepository.findAll(PageRequest.of(0, 100)))
                .willReturn(PageImpl(listOf(TestObjectFactory.getRandomEployee("EMP001"))))

        val employeeList = employeeService.findAllEmployee()
        assertThat(employeeList.first().empId).isEqualTo("EMP001")
    }


    @Test
    fun `saveEmployee should call repository save method`() {
        employeeService.saveEmployee(TestObjectFactory.getRandomEployee())
        Mockito.verify(employeeRepository, Mockito.times(1)).save(any(Employee::class.java))
    }



}