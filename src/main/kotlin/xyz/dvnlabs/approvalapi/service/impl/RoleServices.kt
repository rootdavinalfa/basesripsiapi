/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.dvnlabs.approvalapi.core.exception.ResourceExistsException
import xyz.dvnlabs.approvalapi.core.exception.ResourceNotFoundException
import xyz.dvnlabs.approvalapi.dao.RoleDAO
import xyz.dvnlabs.approvalapi.entity.Role
import xyz.dvnlabs.approvalapi.service.RoleService

@Service
@Transactional
class RoleServices : RoleService {

    @Autowired
    private lateinit var roleDAO: RoleDAO

    override fun findById(id: Int): Role? {
        return roleDAO.findById(id)
            .orElseThrow { ResourceNotFoundException("Role not found") }
    }

    override fun save(entity: Role): Role {
        val newId = roleDAO.findFirstByOrderByIdDesc()?.let {
            return@let it.id + 1
        } ?: kotlin.run {
            return@run 1
        }

        entity.id = newId

        if (roleDAO.existsByRoleName(entity.roleName)) {
            throw ResourceExistsException("Role ${entity.roleName} has been created!")
        }

        return roleDAO.save(entity)
    }

    override fun update(entity: Role): Role {
        return roleDAO.findById(entity.id)
            .map {
                return@map save(it)
            }
            .orElseThrow { ResourceNotFoundException("Role ${entity.roleName} tidak ada") }
    }

    override fun findAll(): MutableList<Role> {
        return roleDAO.findAll()
    }

    override fun findAllPage(pageable: Pageable): Page<Role> {
        return roleDAO.findAll(pageable)
    }

    override fun delete(id: Int) {
        return roleDAO.findById(id).map { rowExist ->
            roleDAO.deleteById(rowExist.id)
        }.orElseThrow { ResourceNotFoundException("Role not found") }
    }

    override fun existById(id: Int): Boolean {
        return roleDAO.existsById(id)
    }

    override fun getRoleByName(name: String): Role? {
        return roleDAO.findByRoleName(name)
    }
}