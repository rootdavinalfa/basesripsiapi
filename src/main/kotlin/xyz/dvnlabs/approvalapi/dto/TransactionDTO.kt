/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.dto

import xyz.dvnlabs.approvalapi.entity.TransactionDetail
import xyz.dvnlabs.approvalapi.entity.User

data class TransactionDTO(
    var idTransaction: Long = 0,
    var transactionName: String = "",
    var statusFlag: String = "1",
    var message: String = "",
    var transactionDetails: List<TransactionDetail>? = null,
    var userRequest: String? = null,
    var userApprove: String? = null,
    var userDelivery: String? = null,
    var userCancel: String? = null,
) : AuditDTO()
