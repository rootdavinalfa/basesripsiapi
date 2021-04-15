/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.entity

import java.io.Serializable

data class TransactionDetailPK(
    var idTransaction: Long = 0,
    var noDetail: Long = 0
) : Serializable
