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
data class Transaction(
    @Id var idTransaction: Long = 0,
    var transactionName: String = "",
    /**
     * Status flag
     * 1 = OPEN
     * 2 = ON PROGRESS WAREHOUSE
     * 3 = ON DELIVERY
     * 4 = DELIVERED
     * 5 = REJECTED
     */
    var statusFlag: String = "1",
    var message: String = "",
    @DBRef var transactionDetails: MutableList<TransactionDetail>? = null,
    @DBRef var userRequest: User? = null,
    @DBRef var userApprove: User? = null,
    @DBRef var userDelivery: User? = null,
) : AuditEntity()
