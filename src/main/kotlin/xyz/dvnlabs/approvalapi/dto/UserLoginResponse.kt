/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.dto

import org.springframework.security.core.authority.SimpleGrantedAuthority

class UserLoginResponse(var token: String, var username: String, var authorities: List<SimpleGrantedAuthority>?) {
    var authToken = "Bearer $token"
}