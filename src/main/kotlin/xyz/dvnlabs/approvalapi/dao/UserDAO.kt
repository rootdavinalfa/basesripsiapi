package xyz.dvnlabs.approvalapi.dao

import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import xyz.dvnlabs.approvalapi.entity.User

@Repository
interface UserDAO : GenericDAO<User, String> {

    fun findByUserName(userName: String): User?

    fun existsByIdOrUserName(id: String, userName: String): Boolean


    fun existsByEmail(email: String): Boolean

    fun existsByUserName(userName: String): Boolean

    fun findFirstByOrderByIdDesc(): User?

}