/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.core.security

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import xyz.dvnlabs.approvalapi.entity.User

class UserDetails(
    private var userName: String,
    private var password: String,
    private var authorities: List<SimpleGrantedAuthority>?
) : UserDetails {

    override fun getAuthorities(): List<SimpleGrantedAuthority>? {
        return authorities
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return userName
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    companion object {
        fun build(user: User): xyz.dvnlabs.approvalapi.core.security.UserDetails {
            val authoritiess = user.roles?.map { SimpleGrantedAuthority(it.roleName) }?.toList()
            return UserDetails(user.userName, user.password, authoritiess)
        }
    }
}