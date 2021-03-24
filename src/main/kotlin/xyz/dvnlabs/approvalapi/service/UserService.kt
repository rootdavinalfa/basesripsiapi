/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.service

import org.springframework.security.core.userdetails.UserDetailsService
import xyz.dvnlabs.approvalapi.core.security.UserDetails
import xyz.dvnlabs.approvalapi.dto.LoginRequest
import xyz.dvnlabs.approvalapi.dto.UserLoginResponse
import xyz.dvnlabs.approvalapi.dto.UserRegister
import xyz.dvnlabs.approvalapi.entity.Role
import xyz.dvnlabs.approvalapi.entity.User

interface UserService : GenericService<User, String>,UserDetailsService {



    fun register(userRegister: UserRegister): User

    fun signin(loginRequest: LoginRequest): UserLoginResponse?

    fun attachRole(role : Int,id : String) : User

    fun revokeRole(role : Int,id : String) : User

}