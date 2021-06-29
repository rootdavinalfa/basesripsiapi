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
import org.springframework.data.mongodb.core.query.Query
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.dvnlabs.approvalapi.core.bus.RxBus
import xyz.dvnlabs.approvalapi.core.exception.ResourceExistsException
import xyz.dvnlabs.approvalapi.core.exception.ResourceNotFoundException
import xyz.dvnlabs.approvalapi.core.helper.CommonHelper
import xyz.dvnlabs.approvalapi.core.helper.CopyNonNullProperties
import xyz.dvnlabs.approvalapi.core.specification.QueryHelper
import xyz.dvnlabs.approvalapi.core.specification.QueryOperation
import xyz.dvnlabs.approvalapi.dao.NotificationDAO
import xyz.dvnlabs.approvalapi.dto.NotificationSenderDTO
import xyz.dvnlabs.approvalapi.dto.TargetKind
import xyz.dvnlabs.approvalapi.entity.Notification
import xyz.dvnlabs.approvalapi.entity.Role
import xyz.dvnlabs.approvalapi.entity.Transaction
import xyz.dvnlabs.approvalapi.service.NotificationService
import xyz.dvnlabs.approvalapi.service.RoleService
import xyz.dvnlabs.approvalapi.service.TransactionService
import xyz.dvnlabs.approvalapi.service.UserService

@Service
@Transactional(rollbackFor = [Exception::class])
class NotificationServices : NotificationService {

    @Autowired
    private lateinit var simpMessagingTemplate: SimpMessagingTemplate

    @Autowired
    private lateinit var notificationDAO: NotificationDAO

    @Autowired
    private lateinit var transactionService: TransactionService

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var roleService: RoleService

    companion object {
        const val SERVICE_NAME = "Notification"
    }

    init {
        RxBus.listen(NotificationSenderDTO::class.java)
            .subscribe {
                when (it.targetKind) {
                    TargetKind.INDIVIDUAL -> {
                        simpMessagingTemplate
                            .convertAndSendToUser(
                                it.target,
                                "/queue/notification",
                                listenTransaction(it.data as Transaction, it.target, it)
                            )
                    }
                    TargetKind.ROLE -> {

                        roleService.getRoleByName(it.target)
                            ?.let { role ->
                                userService.findAllWithQuery(
                                    QueryHelper()
                                        .addOne("roles", Role(id = role.id), QueryOperation.EQUAL)
                                        .and()
                                        .buildQuery()
                                ).forEach { user ->
                                    it.target = user.userName
                                    simpMessagingTemplate
                                        .convertAndSendToUser(
                                            user.userName,
                                            "/queue/notification",
                                            listenTransaction(it.data as Transaction, user.userName, it)
                                        )
                                }
                            }
                    }
                }
            }
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

    override fun findAllWithQuery(query: Query): List<Notification> {
        return notificationDAO.findAllQuery(query, Notification::class.java)
    }

    override fun findAllPage(pageable: Pageable): Page<Notification> {
        return notificationDAO.findAll(pageable)
    }

    override fun delete(id: String) {
        return notificationDAO.findById(id).map { rowExist ->
            notificationDAO.deleteById(rowExist.id)
        }.orElseThrow { ResourceNotFoundException(SERVICE_NAME, id) }
    }

    override fun attachTransaction(idTransaction: Long, idNotification: String): Notification? {
        val notification = findById(idNotification)
        val transaction = transactionService.findById(idTransaction)
        notification?.transaction = transaction
        return notification?.let { update(it) }
    }

    override fun detachTransaction(idNotification: String): Notification? {
        val notification = findById(idNotification)
        notification?.transaction = null
        return notification?.let { update(it) }
    }

    override fun listenTransaction(
        transaction: Transaction?,
        userName: String,
        senderDTO: NotificationSenderDTO?
    ): List<Notification> {
        if (senderDTO != null && transaction != null) {

            save(
                Notification(
                    title = senderDTO.title,
                    body = senderDTO.body,
                    sender = senderDTO.userNameSender,
                    target = senderDTO.target,
                    transaction = transaction
                )
            )

            return findAllWithQuery(
                QueryHelper()
                    .addOne("target", userName, QueryOperation.EQUAL)
                    .and()
                    .buildQuery()
            )
        }

        //Return notification list
        if (userName.isNotEmpty()) {
            return findAllWithQuery(
                QueryHelper()
                    .addOne("target", userName, QueryOperation.EQUAL)
                    .and()
                    .buildQuery()
            )
        }

        return emptyList()
    }

}