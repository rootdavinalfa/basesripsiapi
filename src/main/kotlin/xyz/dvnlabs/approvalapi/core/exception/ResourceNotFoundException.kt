/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.core.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NOT_FOUND)
data class ResourceNotFoundException(val messageStr: String) : Exception(messageStr) {
    constructor(serviceName: String, identifier: Any?)
            : this("$serviceName is not found for: $identifier")
}