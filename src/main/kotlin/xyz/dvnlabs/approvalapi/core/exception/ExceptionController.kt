/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.core.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ExceptionController {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun resourceNotFoundException(ex: ResourceNotFoundException, request: WebRequest): ResponseEntity<*>? {
        val errorDetails = ErrorsResponse(ex.message, ex.cause, request.getDescription(false))
        return ResponseEntity<Any>(errorDetails, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(ResourceExistsException::class)
    fun resourceExistException(ex: ResourceExistsException, request: WebRequest): ResponseEntity<*>? {
        val errorDetails = ErrorsResponse(ex.message, ex.cause, request.getDescription(false))
        return ResponseEntity<Any>(errorDetails, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(InvalidRequestException::class)
    fun resourceInvalidException(ex: InvalidRequestException, request: WebRequest): ResponseEntity<*>? {
        val errorDetails = ErrorsResponse(ex.message, ex.cause, request.getDescription(false))
        return ResponseEntity<Any>(errorDetails, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun resourceUnauthorizedException(ex: UnauthorizedException, request: WebRequest): ResponseEntity<*>? {
        val errorDetails = ErrorsResponse(ex.message, ex.cause, request.getDescription(false))
        return ResponseEntity<Any>(errorDetails, HttpStatus.NOT_FOUND)
    }

}