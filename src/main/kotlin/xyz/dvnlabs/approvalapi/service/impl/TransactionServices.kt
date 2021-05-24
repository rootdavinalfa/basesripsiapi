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
import xyz.dvnlabs.approvalapi.core.helper.CopyNonNullProperties
import xyz.dvnlabs.approvalapi.dao.TransactionDAO
import xyz.dvnlabs.approvalapi.entity.Transaction
import xyz.dvnlabs.approvalapi.service.TransactionDetailService
import xyz.dvnlabs.approvalapi.service.TransactionService

@Service
@Transactional(rollbackFor = [Exception::class])
class TransactionServices : TransactionService {

    @Autowired
    private lateinit var transactionDAO: TransactionDAO

    @Autowired
    private lateinit var transactionDetailService: TransactionDetailService

    override fun findById(id: Long): Transaction? {
        return transactionDAO
            .findById(id)
            .orElseThrow {
                ResourceNotFoundException("Transaction not found with id $id")
            }
    }

    override fun save(entity: Transaction): Transaction {
        val max = transactionDAO.firstIDDesc()

        entity.idTransaction = max?.let {
            return@let it + 1
        } ?: kotlin.run {
            return@run 1
        }


        if (transactionDAO.existsById(entity.idTransaction)) {
            throw ResourceExistsException("Data exists!")
        }

        return transactionDAO.save(entity)
    }

    override fun update(entity: Transaction): Transaction {
        return transactionDAO.findById(entity.idTransaction)
            .map {
                CopyNonNullProperties.copyNonNullProperties(entity, it)
                return@map transactionDAO.save(it)
            }
            .orElseThrow {
                ResourceNotFoundException("Transaction not found")
            }
    }

    override fun findAll(): MutableList<Transaction> {
        return transactionDAO.findAll()
    }

    override fun findAllPage(pageable: Pageable): Page<Transaction> {
        return transactionDAO.findAll(pageable)
    }

    override fun delete(id: Long) {
        transactionDAO.findById(id)
            .map {
                return@map transactionDAO.deleteById(it.idTransaction)
            }
            .orElseThrow {
                ResourceNotFoundException("Transaction not found")
            }
    }

    override fun attachDetail(idTransaction: Long, idDetail: String): Transaction? {
        val transaction = findById(idTransaction)
        val detail = transactionDetailService.findById(idDetail)
        transaction?.let {
            if (it.transactionDetails.isNullOrEmpty()) {
                it.transactionDetails = mutableListOf()

                detail?.let { det ->
                    it.transactionDetails?.add(det)
                }

            } else {
                detail?.let { det ->
                    it.transactionDetails?.add(det)
                }
            }
        }

        transaction?.let {
            update(transaction)
        }

        return transaction
    }

    override fun deAttachDetail(idTransaction: Long, idDetail: String): Transaction? {
        val transaction = findById(idTransaction)
        val detail = transactionDetailService.findById(idDetail)
        transaction?.let {
            if (it.transactionDetails.isNullOrEmpty()) {
                it.transactionDetails = mutableListOf()

                detail?.let { det ->
                    it.transactionDetails?.remove(det)
                }

            } else {
                detail?.let { det ->
                    it.transactionDetails?.remove(det)
                }
            }
        }

        transaction?.let {
            update(transaction)
        }

        return transaction
    }

}