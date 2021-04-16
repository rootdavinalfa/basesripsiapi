/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.dto

import xyz.dvnlabs.approvalapi.core.audit.AuditEntity

data class UnitDTO(
    var unitID: String = "",
    var unitName: String = ""
) : AuditDTO()
