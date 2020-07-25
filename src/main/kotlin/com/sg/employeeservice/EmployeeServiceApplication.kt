package com.sg.employeeservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.sg"])
class EmployeeServiceApplication

fun main(args: Array<String>) {
	runApplication<EmployeeServiceApplication>(*args)
}
