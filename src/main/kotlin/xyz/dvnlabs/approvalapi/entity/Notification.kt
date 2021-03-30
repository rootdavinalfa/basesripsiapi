/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import xyz.dvnlabs.approvalapi.core.audit.AuditEntity
import java.util.*

@Document
data class Notification(
    @Id var id : String = "",
    var transactionID : String = ""
) : AuditEntity()