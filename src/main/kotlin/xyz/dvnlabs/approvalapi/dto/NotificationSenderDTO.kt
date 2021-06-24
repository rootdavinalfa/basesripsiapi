/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.dto

data class NotificationSenderDTO(
    var userNameSender: String,
    var target: String,
    var targetKind: TargetKind = TargetKind.INDIVIDUAL,
    var title: String,
    var body: String,
    var data: Any
)
