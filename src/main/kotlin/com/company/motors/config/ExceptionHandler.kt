package com.company.motors.config

import com.company.motors.config.exception.GenericException
import com.company.motors.extensions.CompanionLogger
import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.bind.annotation.ControllerAdvice
import io.micrometer.tracing.Tracer
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@ControllerAdvice
class ExceptionHandler(private val httpServletRequest: HttpServletRequest,
                       private val tracer: Tracer?) {


    @ExceptionHandler(Throwable::class)
    fun handle(ex: Throwable): ResponseEntity<ApiErrorResponse> {
        log { error(HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase, ex) }
        return buildResponseError(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ex,
            ErrorCatalog.INTERNAL_SERVER_ERROR.name,
            ErrorCatalog.INTERNAL_SERVER_ERROR.defaultMessage,
        )
    }

    @ExceptionHandler(GenericException::class)
    fun handle(ex: GenericException): ResponseEntity<ApiErrorResponse> {
        log { error(HttpStatus.INTERNAL_SERVER_ERROR.reasonPhrase, ex) }
        return buildResponseError(HttpStatus.INTERNAL_SERVER_ERROR, ex)
    }

    private fun buildResponseError(
        httpStatus: HttpStatus,
        ex: GenericException,
        errorCode: String = ex.errorCode,
        errorMessage: String = ex.message ?: "",
    ): ResponseEntity<ApiErrorResponse> = buildResponseError(
        httpStatus,
        ex as Throwable,
        errorCode,
        errorMessage,
    )


    private fun buildResponseError(
        httpStatus: HttpStatus,
        ex: Throwable,
        errorCode: String,
        errorMessage: String = ex.message ?: "",
    ): ResponseEntity<ApiErrorResponse> {

        val stackTrace: String? = Arrays.toString(ex.stackTrace)
        val traceId: String? = tracer
            ?.currentSpan()
            ?.context()
            ?.traceId()
        val spanId: String? = tracer
            ?.currentSpan()
            ?.context()
            ?.spanId()







        val apiErrorResponse = ApiErrorResponse(
            timestamp = LocalDateTime.now(ZoneOffset.UTC),
            name = httpStatus.reasonPhrase,
            message = errorMessage,
            status = httpStatus.value(),
            code = errorCode,
            resource = httpServletRequest.requestURI,
            spanId = spanId,
            traceId = traceId,
            stackTrace = stackTrace
        )

        return ResponseEntity(apiErrorResponse, httpStatus)
    }

    data class ApiErrorResponse(
        val name: String,
        val status: Int,
        val timestamp: LocalDateTime,
        val code: String,
        val resource: String,
        val message: String,

        @field:JsonInclude(JsonInclude.Include.NON_NULL)
        val spanId: String? = null,

        @field:JsonInclude(JsonInclude.Include.NON_NULL)
        val traceId: String? = null,

        @field:JsonInclude(JsonInclude.Include.NON_NULL)
        val stackTrace: String? = null
    )

    companion object : CompanionLogger()


}
