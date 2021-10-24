/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.service

import xyz.dvnlabs.approvalapi.dto.NotificationSenderDTO
import xyz.dvnlabs.approvalapi.entity.Notification
import xyz.dvnlabs.approvalapi.entity.Transaction

interface NotificationService : GenericService<Notification, String> {

    fun attachTransaction(idTransaction: Long, idNotification: String): Notification?

    fun detachTransaction(idNotification: String): Notification?

    fun listenTransaction(
        transaction: Transaction?,
        userName: String,
        senderDTO: NotificationSenderDTO?
    ): Notification

    fun publishNotification(notificationSenderDTO: NotificationSenderDTO)

}