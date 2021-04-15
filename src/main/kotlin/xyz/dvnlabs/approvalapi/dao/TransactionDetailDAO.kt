/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.dao

import org.springframework.stereotype.Repository
import xyz.dvnlabs.approvalapi.entity.TransactionDetail
import xyz.dvnlabs.approvalapi.entity.TransactionDetailPK

@Repository
interface TransactionDetailDAO : GenericDAO<TransactionDetail,TransactionDetailPK>{

}