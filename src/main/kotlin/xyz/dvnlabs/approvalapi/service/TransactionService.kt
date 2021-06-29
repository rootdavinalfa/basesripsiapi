/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.service

import xyz.dvnlabs.approvalapi.entity.Transaction
import xyz.dvnlabs.approvalapi.entity.TransactionDetail

interface TransactionService : GenericService<Transaction, Long> {

    fun attachDetail(idTransaction: Long, idDetail: String): Transaction?

    fun deAttachDetail(idTransaction: Long, idDetail: String): Transaction?

    fun createTransaction(
        transactionDTO: Transaction,
        transactionDetails: List<TransactionDetail>?
    ): Transaction?

    fun validationTransaction(
        idTransaction: Long
    ): Transaction?

    fun attachDelivery(
        idTransaction: Long,
        idUser : String
    ): Transaction?
}