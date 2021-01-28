package xyz.dvnlabs.approvalapi.dao

import xyz.dvnlabs.approvalapi.entity.Role

interface RoleDAO : GenericDAO<Role, Int> {

    fun findFirstByOrderByIdDesc(): Role?

    fun existsByRoleName(name: String): Boolean

}