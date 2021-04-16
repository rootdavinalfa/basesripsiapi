/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.core.audit

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.util.*


abstract class AuditEntity(
    @CreatedBy
    var createdBy: String? = null,
    @CreatedDate
    var createdDate: Date = Date(),
    @LastModifiedBy
    var lastModifiedBy: String = "",
    @LastModifiedDate
    var lastModifiedDate: Date = Date(),
)