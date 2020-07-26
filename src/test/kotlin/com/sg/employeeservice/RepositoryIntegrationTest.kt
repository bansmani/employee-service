package com.sg.employeeservice

import com.sg.employeeservice.repository.EmployeeRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest


@DataJpaTest
class RepositoryIntegrationTest {

    @Autowired private lateinit var employeeRepository: EmployeeRepository

    @Test
    fun `can save employee object in database and retire back`(){
        val randomEployee = TestObjectFactory.getRandomEployee()
        employeeRepository.save(randomEployee)
        Assertions.assertThat(employeeRepository.findAll().first()).isEqualTo(randomEployee)

    }

}