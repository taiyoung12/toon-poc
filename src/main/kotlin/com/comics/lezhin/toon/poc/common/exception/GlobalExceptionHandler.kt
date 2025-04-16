package com.comics.lezhin.toon.poc.common.exception

import com.comics.lezhin.toon.poc.common.code.CommonCode
import com.comics.lezhin.toon.poc.common.response.Response
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(BaseException::class)
    fun handleBusinessException(e: BaseException): ResponseEntity<Response<Any>> {
        log.error("Business exception occurred", e)

        val body: Any =
            if (e is ValidationException) {
                e.errors
            } else {
                e.message ?: "알 수 없는 오류"
            }

        val response = Response.error(e.code, body)

        return ResponseEntity(response, HttpStatus.valueOf(e.code.getHttpStatus()))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Response<Map<String, String>>> {
        val errors =
            ex.bindingResult
                .fieldErrors
                .associate { it.field to (it.defaultMessage ?: "잘못된 값입니다") }

        val response = Response.error(CommonCode.VALIDATION_ERROR, errors)
        return ResponseEntity(response, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(e: Exception): ResponseEntity<Response<String>> {
        log.error("Unexpected error occurred", e)
        val response = Response.error(CommonCode.INTERNAL_ERROR, e.message ?: "Unknown error")
        return ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandler(ex: NoHandlerFoundException): ResponseEntity<Response<String>> {
        log.error("No handler found", ex)
        val message = "요청한 리소스 [${ex.requestURL}]를 찾을 수 없습니다."
        val response = Response.error(CommonCode.RESOURCE_NOT_FOUND, message)
        return ResponseEntity(response, HttpStatus.NOT_FOUND)
    }
}
