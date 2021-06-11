/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.dto

import java.util.*

data class TestDTO(
    var username: String = "",
    var message : String = "",
    var loggedTime : Date = Date()
)
