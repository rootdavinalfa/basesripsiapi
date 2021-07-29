/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import xyz.dvnlabs.approvalapi.core.specification.QueryHelper
import xyz.dvnlabs.approvalapi.core.specification.QueryOperation
import xyz.dvnlabs.approvalapi.dto.UserNoPasswordDTO
import xyz.dvnlabs.approvalapi.entity.User
import xyz.dvnlabs.approvalapi.mapper.UserMapper
import xyz.dvnlabs.approvalapi.service.RoleService
import xyz.dvnlabs.approvalapi.service.UserService

@RequestMapping("/user")
@RestController
@Api(tags = ["User Controller"])
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var userMapper: UserMapper

    @Autowired
    private lateinit var roleService: RoleService

    @GetMapping("/{id}")
    @ApiOperation("Find by id")
    fun findById(@PathVariable id: String): UserNoPasswordDTO? {
        return userService.findById(id)?.let { userMapper.asUserNoPasswordDTO(it) }
    }

    @GetMapping("/username/{user}")
    @ApiOperation("Find by username")
    fun findByUserName(@PathVariable user: String): UserNoPasswordDTO? {
        return userService.getUserByUsername(user)?.let { userMapper.asUserNoPasswordDTO(it) }
    }

    @PostMapping("/attach-role/{userid}/{roleid}")
    @ApiOperation("Attach role")
    fun attachRole(
        @PathVariable userid: String,
        @PathVariable roleid: Int
    ): UserNoPasswordDTO {
        return userMapper.asUserNoPasswordDTO(userService.attachRole(roleid, userid))
    }

    @PostMapping("/revoke-role/{userid}/{roleid}")
    @ApiOperation("Revoke role")
    fun revokeRole(
        @PathVariable userid: String,
        @PathVariable roleid: Int
    ): UserNoPasswordDTO {
        return userMapper.asUserNoPasswordDTO(userService.revokeRole(roleid, userid))
    }

    @PostMapping("/attach-unit/{userid}/{unitid}")
    @ApiOperation("Attach Unit")
    fun attachUnit(
        @PathVariable userid: String,
        @PathVariable unitid: String
    ): UserNoPasswordDTO {
        return userMapper.asUserNoPasswordDTO(userService.attachUnit(unitid, userid)!!)
    }

    @PostMapping("/revoke-unit/{userid}/{unitid}")
    @ApiOperation("Revoke Unit")
    fun revokeUnit(
        @PathVariable userid: String,
        @PathVariable unitid: String
    ): UserNoPasswordDTO {
        return userMapper.asUserNoPasswordDTO(userService.deAttachUnit(unitid, userid)!!)
    }

    @PutMapping("/{id}")
    @ApiOperation("Update")
    fun update(
        @PathVariable id: String,
        @RequestBody user: User
    ): UserNoPasswordDTO {
        user.id = id
        return userMapper.asUserNoPasswordDTO(userService.update(user))
    }

    @GetMapping("/list")
    @ApiOperation("List")
    fun list(
        @RequestParam(defaultValue = "") roleName: String,
        @RequestParam(defaultValue = "") userName: String
    ): List<UserNoPasswordDTO> {
        val query = QueryHelper()
            .addOne("userName", userName, QueryOperation.EQUAL)

        if (roleName.isNotEmpty()) {
            val role = roleService.getRoleByName(roleName)
            query.addOne("roles", role, QueryOperation.EQUAL)
        }

        return userService.findAllWithQuery(
            query
                .or()
                .buildQuery()
        ).map { userMapper.asUserNoPasswordDTO(it) }
    }

    @GetMapping("/page")
    @ApiOperation("Page")
    fun listPage(pageable: Pageable): Page<UserNoPasswordDTO> {
        return userService.findAllPage(pageable).map { userMapper.asUserNoPasswordDTO(it) }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete")
    fun delete(@PathVariable id: String) {
        userService.delete(id)
    }
}