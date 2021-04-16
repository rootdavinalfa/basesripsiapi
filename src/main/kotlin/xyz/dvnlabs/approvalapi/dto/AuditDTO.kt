/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.dto

import java.util.*

abstract class AuditDTO(
    var createdBy: String = "",
    var createdDate: Date = Date(),
    var lastModifiedBy: String = "",
    var lastModifiedDate: Date = Date()
)
