/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.dto

data class RequestTransactionDTO(
    var transactionDTO: TransactionDTO,
    var transactionDetails : List<TransactionDetailDTO>?
)
