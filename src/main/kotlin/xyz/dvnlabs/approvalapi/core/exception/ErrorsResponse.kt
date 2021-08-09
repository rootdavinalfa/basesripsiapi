/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.core.exception

data class ErrorsResponse(
    val message : String?,
    val exception : Throwable?,
    val details : String?
)
