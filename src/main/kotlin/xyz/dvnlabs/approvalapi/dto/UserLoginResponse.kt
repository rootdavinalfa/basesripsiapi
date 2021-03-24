/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.dto

class UserLoginResponse(var token: String, var username: String, var email: String) {
    var authToken = "Bearer $token"
}