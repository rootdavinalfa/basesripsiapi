/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.dto

import xyz.dvnlabs.approvalapi.entity.Role
import java.util.*

data class UserNoPasswordDTO(
    var id: String = "",
    var email: String = "",
    var userName: String = "",
    var registeredOn: Date = Date(),
    var roles: List<Role>? = null
)
