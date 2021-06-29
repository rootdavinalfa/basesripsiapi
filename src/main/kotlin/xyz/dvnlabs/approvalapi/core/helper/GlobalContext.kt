/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.core.helper

import xyz.dvnlabs.approvalapi.entity.User

class GlobalContext {
    companion object {
        var user: User? = null

        fun getUsername(): String {
            return user?.userName ?: "System"
        }
    }
}