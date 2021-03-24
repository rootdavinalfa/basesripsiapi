/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.dvnlabs.approvalapi.core.exception.ResourceExistsException
import xyz.dvnlabs.approvalapi.core.exception.ResourceNotFoundException
import xyz.dvnlabs.approvalapi.core.exception.UnauthorizedException
import xyz.dvnlabs.approvalapi.core.security.JwtUtils
import xyz.dvnlabs.approvalapi.core.security.UserDetails
import xyz.dvnlabs.approvalapi.dao.RoleDAO
import xyz.dvnlabs.approvalapi.dao.UserDAO
import xyz.dvnlabs.approvalapi.dto.LoginRequest
import xyz.dvnlabs.approvalapi.dto.UserLoginResponse
import xyz.dvnlabs.approvalapi.dto.UserRegister
import xyz.dvnlabs.approvalapi.entity.Role
import xyz.dvnlabs.approvalapi.entity.User
import xyz.dvnlabs.approvalapi.service.UserService
import java.util.*

@Service
@Transactional
class UserServices : UserService {

    @Autowired
    private lateinit var userDAO: UserDAO

    @Autowired
    private lateinit var roleDAO: RoleDAO

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager

    @Autowired
    private lateinit var jwtUtils: JwtUtils

    override fun findById(id: String): User? {
        return userDAO.findById(id).orElseThrow { ResourceNotFoundException("User not found") }
    }

    override fun loadUserByUsername(username: String?): org.springframework.security.core.userdetails.UserDetails {
        if (username != null) {
            userDAO.findByUserName(username)?.let { user ->
                return UserDetails.build(user)
            }
            throw ResourceNotFoundException("$username Not found!")
        }
        throw ResourceNotFoundException("Not found!")
    }

    override fun register(userRegister: UserRegister): User {
        val newPassword = passwordEncoder.encode(userRegister.password)

        if (userDAO.existsByUserName(userRegister.userName)) {
            throw ResourceExistsException("Username is exist ${userRegister.userName}")
        }

        if (userDAO.existsByEmail(userRegister.email)) {
            throw ResourceExistsException("Email is exist ${userRegister.email}")
        }

        return save(User(email = userRegister.email, password = newPassword, userName = userRegister.userName))
    }

    override fun signin(loginRequest: LoginRequest): UserLoginResponse? {
        try {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    loginRequest.username,
                    loginRequest.password
                )
            )

            SecurityContextHolder.getContext().authentication = authentication
            val principal = authentication.principal as UserDetails
            val jwt = jwtUtils.generateToken(authentication)
            return UserLoginResponse(jwt, principal.username, "")
        } catch (e: AuthenticationException) {
            println("Username ${e.message}")
            throw UnauthorizedException(e.message!!)
        }
        return null
    }

    override fun save(entity: User): User {


        val newId = userDAO.findFirstByOrderByIdDesc()?.let {
            return@let it.id.toInt() + 1
        } ?: kotlin.run {
            return@run 1
        }

        entity.id = newId.toString()

        entity.registeredOn = Date()
        return userDAO.save(entity)
    }

    override fun attachRole(role: Int, id: String): User {
        return findById(id)
            ?.let {
                val rolee = roleDAO.findById(role)
                    .orElseThrow {
                        ResourceNotFoundException("Role Not founc")
                    }
                // Get existing roles & add new role
                it.roles = if (it.roles != null) {
                    val roles = it.roles!!.toMutableList()
                    if (roles.contains(rolee)) {
                        throw ResourceExistsException("User has been have requested role!")
                    }
                    roles.add(rolee)
                    roles
                } else {
                    val roles = emptyList<Role>().toMutableList()
                    roles.add(rolee)
                    roles
                }

                return@let update(it)
            } ?: kotlin.run {
            throw ResourceNotFoundException("User not found")
        }
    }

    override fun revokeRole(role: Int, id: String): User {
        return findById(id)
            ?.let {
                val rolee = roleDAO.findById(role)
                    .orElseThrow {
                        ResourceNotFoundException("Role Not founc")
                    }
                // Get existing roles & add new role
                it.roles = if (it.roles != null) {
                    val roles = it.roles!!.toMutableList()
                    roles.remove(rolee)
                    roles
                } else {
                    val roles = emptyList<Role>().toMutableList()
                    roles.remove(rolee)
                    roles
                }

                return@let update(it)
            } ?: kotlin.run {
            throw ResourceNotFoundException("User not found")
        }
    }

    override fun update(entity: User): User {
        if (!userDAO.existsByIdOrUserName(entity.id, entity.userName)) {
            throw ResourceNotFoundException("Username ${entity.userName} tidak ada")
        }
        return userDAO.save(entity)
    }

    override fun findAll(): MutableList<User> {
        return userDAO.findAll()
    }

    override fun findAllPage(pageable: Pageable): Page<User> {
        return userDAO.findAll(pageable)
    }

    override fun delete(id: String) {
        return userDAO.findById(id).map { rowExist ->
            userDAO.deleteById(rowExist.id)
        }.orElseThrow { ResourceNotFoundException("Data user tidak ditemukan") }
    }

}