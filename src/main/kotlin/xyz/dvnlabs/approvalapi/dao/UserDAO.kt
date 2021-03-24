/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.dao

import org.springframework.stereotype.Repository
import xyz.dvnlabs.approvalapi.entity.User

@Repository
interface UserDAO : GenericDAO<User, String> {

    fun findByUserName(userName: String): User?

    fun findByUserNameOrId(userName: String, id: String): User?

    fun existsByIdOrUserName(id: String, userName: String): Boolean


    fun existsByEmail(email: String): Boolean

    fun existsByUserName(userName: String): Boolean

    fun findFirstByOrderByIdDesc(): User?

}