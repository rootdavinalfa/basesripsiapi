/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.dvnlabs.approvalapi.core.exception.ResourceExistsException
import xyz.dvnlabs.approvalapi.core.exception.ResourceNotFoundException
import xyz.dvnlabs.approvalapi.core.helper.CopyNonNullProperties
import xyz.dvnlabs.approvalapi.dao.DrugDAO
import xyz.dvnlabs.approvalapi.entity.Drugs
import xyz.dvnlabs.approvalapi.service.DrugsService

@Service
@Transactional(rollbackFor = [Exception::class])
class DrugsServices : DrugsService {

    companion object {
        private const val SERVICE_NAME = "Drug data"
    }

    @Autowired
    private lateinit var drugDAO: DrugDAO


    override fun findById(id: Long): Drugs? {
        return drugDAO.findById(id)
            .orElseThrow {
                ResourceNotFoundException("$SERVICE_NAME not found with ID : $id")
            }
    }

    override fun save(entity: Drugs): Drugs {
        entity.idDrug = drugDAO.findFirstByOrderByIdDrugDesc()
            ?.let {
                it.idDrug + 1
            } ?: 1

        if (drugDAO.existsById(entity.idDrug)) {
            throw ResourceExistsException("$SERVICE_NAME is exist with ID ${entity.idDrug}")
        }

        return drugDAO.save(entity)
    }

    override fun update(entity: Drugs): Drugs {
        return drugDAO.findById(entity.idDrug)
            .map {
                CopyNonNullProperties.copyNonNullProperties(entity, it)
                return@map drugDAO.save(it)
            }
            .orElseThrow {
                ResourceNotFoundException("$SERVICE_NAME not found")
            }
    }

    override fun findAll(): MutableList<Drugs> {
        return drugDAO.findAll()
    }

    override fun findAllPage(pageable: Pageable): Page<Drugs> {
        return drugDAO.findAll(pageable)
    }

    override fun findAllPageWithQuery(pageable: Pageable, query: Query): Page<Drugs> {
        return drugDAO.findAllQueryPage(query, pageable, Drugs::class.java)
    }

    override fun delete(id: Long) {
        return drugDAO.findById(id).map { rowExist ->
            drugDAO.deleteById(rowExist.idDrug)
        }.orElseThrow { ResourceNotFoundException("$SERVICE_NAME not found!") }
    }

    override fun isInitialized(): Boolean {
        return drugDAO.count() > 0
    }
}