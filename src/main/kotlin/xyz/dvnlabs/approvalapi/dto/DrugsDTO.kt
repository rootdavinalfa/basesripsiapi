/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.dto

data class DrugsDTO(
    var idDrug: Long = 0,
    var drugName: String = "",
    var classified: String = "",
    var qty: Double = 0.0,
) : AuditDTO()