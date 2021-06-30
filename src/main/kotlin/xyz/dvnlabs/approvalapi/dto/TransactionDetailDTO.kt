/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.dto

import xyz.dvnlabs.approvalapi.entity.Drugs

data class TransactionDetailDTO(
    var id: String = "",
    var detailRequest: String = "",
    var drug: Drugs? = null,
    var qty: Double = 0.0,
    var realQty: Double = 0.0
) : AuditDTO()