@file:Suppress("unused")

package com.sg.employeeservice.exceptions

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@ControllerAdvice
class GlobalExceptionHandler {

    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(EmployeeNotFoundException::class)
    fun resourceNotFoundExceptionResponse(response: HttpServletResponse,
                                          request: HttpServletRequest,
                                          ex: Exception): ResponseEntity<CustomErrorResponse> {
        logger.error(ex.message, ex)
        return buildCustomErrorResponse(HttpStatus.NOT_FOUND, ex, request)
    }


    @ExceptionHandler(EmployeeAlreadyExistsException::class, IllegalArgumentException::class)
    fun badRequestExceptionResponse(response: HttpServletResponse,
                                    request: HttpServletRequest,
                                    ex: Exception): ResponseEntity<CustomErrorResponse> {
        logger.error(ex.message, ex)
        return buildCustomErrorResponse(HttpStatus.BAD_REQUEST, ex, request)
    }

    fun buildCustomErrorResponse(httpStatus: HttpStatus, ex: Exception, request: HttpServletRequest): ResponseEntity<CustomErrorResponse> {
        return ResponseEntity(CustomErrorResponse(
                status = httpStatus.value(),
                error = httpStatus.reasonPhrase,
                message = ex.cause?.message ?: ex.message ?: "",
                path = request.requestURI), httpStatus)
    }
}


class CustomErrorResponse(
        val timestamp: LocalDateTime = LocalDateTime.now(),
        val status: Int,
        val error: String,
        val message: String,
        val path: String
)

class EmployeeAlreadyExistsException(message: String) : RuntimeException(message)
class EmployeeNotFoundException(message: String) : RuntimeException(message)


