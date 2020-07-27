package com.sg.employeeservice

import com.sg.employeeservice.repository.EmployeeRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.PageRequest


@DataJpaTest
class RepositoryIntegrationTest {

    @Autowired
    private lateinit var employeeRepository: EmployeeRepository

    @Test
    fun `save one employee retire back with findAll`() {
        val randomEployee = TestObjectFactory.getRandomEployee()
        employeeRepository.save(randomEployee)
        assertThat(employeeRepository.findAll().first()).isEqualTo(randomEployee)

    }


    @Test
    fun `save multiple employee retire back with findAll`() {
        val employees = TestObjectFactory.getRandomEployees(10)
        employeeRepository.saveAll(employees)
        val allEmp = employeeRepository.findAll()
        assertThat(allEmp.count()).isEqualTo(10)
        assertThat(allEmp).containsAll(employees)
    }


    @Test
    fun `save multiple employee and find One by Id`() {
        val employees = TestObjectFactory.getRandomEployees(10)
        employeeRepository.saveAll(employees)
        val employee = employeeRepository.findById(employees[3].empId)
        assertThat(employee.get().empId).isEqualTo(employees[3].empId)
    }

    @Test
    fun `employee list should fetch only for given page size from database`() {

        val employees = TestObjectFactory.getRandomEployees(10)
        employeeRepository.saveAll(employees)
        val only2Employee = employeeRepository.findAll(PageRequest.of(0, 2))
        assertThat(only2Employee.numberOfElements).isEqualTo(2)
        val only3Employee = employeeRepository.findAll(PageRequest.of(1, 3))
        assertThat(only3Employee.numberOfElements).isEqualTo(3)
        assertThat(only3Employee.totalElements).isEqualTo(10)

    }


    @Test
    fun `should throw DataIntegrityViolationException when trying to recreate duplicate record`() {
        val randomEployee1 = TestObjectFactory.getRandomEployee("EMP123")
        val randomEployee2 = TestObjectFactory.getRandomEployee("EMP123")
        employeeRepository.save(randomEployee1)
        Assertions.assertThrows(DataIntegrityViolationException::class.java, { employeeRepository.save(randomEployee2) })
    }


}