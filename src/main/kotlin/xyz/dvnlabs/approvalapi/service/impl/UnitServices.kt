/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.rest.webmvc.ResourceNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.dvnlabs.approvalapi.core.exception.ResourceExistsException
import xyz.dvnlabs.approvalapi.core.helper.CommonHelper
import xyz.dvnlabs.approvalapi.core.helper.CopyNonNullProperties
import xyz.dvnlabs.approvalapi.dao.UnitDAO
import xyz.dvnlabs.approvalapi.entity.Unit
import xyz.dvnlabs.approvalapi.service.UnitService

@Service
@Transactional(rollbackFor = [Exception::class])
class UnitServices : UnitService {

    @Autowired
    private lateinit var unitDAO: UnitDAO

    override fun findById(id: String): Unit? {
        return unitDAO.findById(id)
            .orElseThrow {
                ResourceNotFoundException("Unit not found")
            }
    }

    override fun save(entity: Unit): Unit {
        val max = unitDAO.firstIDDesc()

        entity.unitID = CommonHelper.getStringSeq(max?.let {
            return@let it
        } ?: kotlin.run {
            return@run "UNIT000"
        }, "UNIT", "", 3)


        if (unitDAO.existsById(entity.unitID)) {
            throw ResourceExistsException("Data exists!")
        }
        return unitDAO.save(entity)
    }

    override fun update(entity: Unit): Unit {
        return unitDAO.findById(entity.unitID)
            .map {
                CopyNonNullProperties.copyNonNullProperties(entity, it)
                return@map unitDAO.save(it)
            }
            .orElseThrow {
                ResourceNotFoundException("Unit not found")
            }
    }

    override fun findAll(): MutableList<Unit> {
        return unitDAO.findAll()
    }

    override fun findAllPage(pageable: Pageable): Page<Unit> {
        return unitDAO.findAll(pageable)
    }

    override fun delete(id: String) {
        unitDAO.findById(id)
            .map {
                return@map unitDAO.deleteById(it.unitID)
            }
            .orElseThrow {
                ResourceNotFoundException("Unit not found")
            }
    }
}