package com.sg.employeeservice.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.databind.ObjectMapper
import com.sg.employeeservice.dto.EmployeeDTO
import org.springframework.data.domain.Persistable
import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.Id

@Suppress("MemberVisibilityCanBePrivate")
@Entity
class Employee(@Id val empId: String,
               val firstName: String,
               val lastName: String,
               val gender: Gender,
               val dob: LocalDate,
               val department: String,
               val action: RecordAction = RecordAction.CREATE) : Persistable<String> {

    fun toDTO() = EmployeeDTO(empId, firstName, lastName, gender, dob, department)


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Employee

        if (empId != other.empId) return false

        return true
    }

    override fun hashCode(): Int {
        return empId.hashCode()
    }

    @JsonIgnore
    override fun getId(): String? {
        return empId

    }

    @JsonIgnore
    override fun isNew(): Boolean {
        return action == RecordAction.CREATE
    }


}
