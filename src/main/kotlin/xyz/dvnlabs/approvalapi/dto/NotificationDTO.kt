/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.dto

import xyz.dvnlabs.approvalapi.entity.Transaction


data class NotificationDTO(
    var id: String = "",
    var title: String = "",
    var body: String = "",
    var sender: String = "",
    var target: String = "",
    /**
     * NOTIFICATION FLAG
     *
     * 0 = Published Not Read
     *
     * 1 = Published Read
     */
    var flag: String = "0",
    var transaction: Transaction? = null
) : AuditDTO()
