/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.dvnlabs.approvalapi.core.exception.ResourceExistsException
import xyz.dvnlabs.approvalapi.core.exception.ResourceNotFoundException
import xyz.dvnlabs.approvalapi.core.helper.CommonHelper
import xyz.dvnlabs.approvalapi.core.helper.CopyNonNullProperties
import xyz.dvnlabs.approvalapi.dao.TransactionDetailDAO
import xyz.dvnlabs.approvalapi.entity.TransactionDetail
import xyz.dvnlabs.approvalapi.service.TransactionDetailService

@Service
@Transactional(rollbackFor = [Exception::class])
class TransactionDetailServices : TransactionDetailService {

    companion object {
        val SERVICE_NAME = "Transaction Detail";
    }

    @Autowired
    private lateinit var transactionDetailDAO: TransactionDetailDAO

    override fun findById(id: String): TransactionDetail? {
        return transactionDetailDAO
            .findById(id)
            .orElseThrow { ResourceNotFoundException("$SERVICE_NAME not found $id") }
    }

    override fun save(entity: TransactionDetail): TransactionDetail {

        entity.id = CommonHelper.getStringSeq(
            transactionDetailDAO.firstIDDesc(
                CommonHelper
                    .convertDateToStringWithPattern(
                        CommonHelper.getCurrentDate(), "yyyy-MM-dd"
                    )
            )
                ?.let {
                    return@let it
                } ?: "XXXXXXXXX000000", CommonHelper.getCurrentDate(), "TRX", "", 6, false, "yyMMdd"
        )


        if (existById(entity.id)) {
            throw ResourceExistsException("$SERVICE_NAME is exist!")
        }
        return transactionDetailDAO.save(entity)
    }

    override fun update(entity: TransactionDetail): TransactionDetail {
        return transactionDetailDAO.findById(entity.id)
            .map {
                CopyNonNullProperties.copyNonNullProperties(entity, it)
                return@map transactionDetailDAO.save(it)
            }
            .orElseThrow {
                ResourceNotFoundException("$SERVICE_NAME not found")
            }
    }

    override fun findAll(): MutableList<TransactionDetail> {
        return transactionDetailDAO.findAll()
    }

    override fun findAllPage(pageable: Pageable): Page<TransactionDetail> {
        return transactionDetailDAO.findAll(pageable)
    }

    override fun delete(id: String) {
        transactionDetailDAO.findById(id)
            .map {
                return@map transactionDetailDAO.deleteById(it.id)
            }
            .orElseThrow {
                ResourceNotFoundException("Transaction not found")
            }
    }

    override fun existById(id: String): Boolean {
        return transactionDetailDAO.existsById(id)
    }

}