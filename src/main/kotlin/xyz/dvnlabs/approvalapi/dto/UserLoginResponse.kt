package xyz.dvnlabs.approvalapi.dto

class UserLoginResponse(var token: String, var username: String, var email: String) {
    var authToken = "Bearer $token"
}