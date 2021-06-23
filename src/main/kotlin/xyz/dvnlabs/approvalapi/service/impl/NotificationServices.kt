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
import xyz.dvnlabs.approvalapi.dao.NotificationDAO
import xyz.dvnlabs.approvalapi.entity.Notification
import xyz.dvnlabs.approvalapi.service.NotificationService

@Service
@Transactional(rollbackFor = [Exception::class])
class NotificationServices : NotificationService {

    @Autowired
    private lateinit var notificationDAO: NotificationDAO

    companion object {
        const val SERVICE_NAME = "Notification"
    }

    override fun findById(id: String): Notification? {
        return notificationDAO.findById(id)
            .orElseThrow { throw ResourceNotFoundException("$SERVICE_NAME is not found") }
    }

    override fun save(entity: Notification): Notification {
        val max = notificationDAO.firstIDDesc()

        entity.id = CommonHelper.getStringSeq(max?.let {
            return@let it
        } ?: kotlin.run {
            return@run "N00000"
        }, "N", "", 5)

        if (notificationDAO.existsById(entity.id)) {
            throw ResourceExistsException("$SERVICE_NAME is exist")
        }

        return notificationDAO.save(entity)
    }

    override fun update(entity: Notification): Notification {
        return notificationDAO.findById(entity.id)
            .map {
                CopyNonNullProperties.copyNonNullProperties(entity, it)
                return@map save(it)
            }
            .orElseThrow { ResourceNotFoundException(SERVICE_NAME, entity.id) }
    }

    override fun findAll(): MutableList<Notification> {
        return notificationDAO.findAll()
    }

    override fun findAllPage(pageable: Pageable): Page<Notification> {
        return notificationDAO.findAll(pageable)
    }

    override fun delete(id: String) {
        return notificationDAO.findById(id).map { rowExist ->
            notificationDAO.deleteById(rowExist.id)
        }.orElseThrow { ResourceNotFoundException(SERVICE_NAME, id) }
    }

}