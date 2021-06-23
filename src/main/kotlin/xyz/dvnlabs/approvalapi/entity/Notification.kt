/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import xyz.dvnlabs.approvalapi.core.audit.AuditEntity

@Document
data class Notification(
    @Id
    var id: String = "",
    var title: String = "",
    var body: String = "",
    /**
     * NOTIFICATION FLAG
     *
     * 0 = Published Not Read
     *
     * 1 = Published Read
     */
    var flag: String = "0",
    var idTransaction: Long = 0
) : AuditEntity()
