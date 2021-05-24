/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document
import xyz.dvnlabs.approvalapi.core.audit.AuditEntity

@Document
data class TransactionDetail(
    @Id var id: String = "",
    var detailRequest: String = "",
    @DBRef(lazy = true)
    var drug: Drugs? = null,
    var qty: Double = 0.0
) : AuditEntity()
