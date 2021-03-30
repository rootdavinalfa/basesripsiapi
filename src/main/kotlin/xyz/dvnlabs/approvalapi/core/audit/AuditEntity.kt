/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.core.audit

import org.springframework.data.annotation.*
import java.util.*


abstract class AuditEntity(
    @CreatedBy
    var createdBy: String = "",
    @CreatedDate
    var createdDate: Date = Date(),
    @LastModifiedBy
    var lastModifiedBy: String = "",
    @LastModifiedDate
    var lastModifiedDate: Date = Date(),
    @Version
    var version: Long = 0
)