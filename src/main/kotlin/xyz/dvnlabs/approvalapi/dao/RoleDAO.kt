/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.dao

import xyz.dvnlabs.approvalapi.entity.Role

interface RoleDAO : GenericDAO<Role, Int> {

    fun findFirstByOrderByIdDesc(): Role?

    fun existsByRoleName(name: String): Boolean

}