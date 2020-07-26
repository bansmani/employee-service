package com.sg.employeeservice

import com.sg.employeeservice.repository.EmployeeRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest


@DataJpaTest
class RepositoryIntegrationTest {

    @Autowired private lateinit var employeeRepository: EmployeeRepository

    @Test
    fun `save one employee retire back with findAll`(){
        val randomEployee = TestObjectFactory.getRandomEployee()
        employeeRepository.save(randomEployee)
        assertThat(employeeRepository.findAll().first()).isEqualTo(randomEployee)

    }


    @Test
    fun `save multiple employee retire back with findAll`(){
        val employees = TestObjectFactory.getRandomEployees(10)
        employeeRepository.saveAll(employees)
        val allEmp = employeeRepository.findAll()
        assertThat(allEmp.count()).isEqualTo(10)
        assertThat(allEmp).containsAll(employees)
    }


    @Test
    fun `save multiple employee and find One by Id`(){
        val employees = TestObjectFactory.getRandomEployees(10)
        employeeRepository.saveAll(employees)
        val employee = employeeRepository.findById(employees[3].empId)
        assertThat(employee.get().empId).isEqualTo(employees[3].empId)
    }


}