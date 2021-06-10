/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.service

import xyz.dvnlabs.approvalapi.entity.Transaction

interface TransactionService : GenericService<Transaction,Long>{

    fun attachDetail(idTransaction : Long, idDetail : String) : Transaction?

    fun deAttachDetail(idTransaction : Long, idDetail : String) : Transaction?

}