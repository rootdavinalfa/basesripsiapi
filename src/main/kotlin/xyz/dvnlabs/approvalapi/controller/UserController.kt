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
import xyz.dvnlabs.approvalapi.entity.User
import xyz.dvnlabs.approvalapi.service.UserService

@RequestMapping("/user")
@RestController
@Api(tags = ["User Controller"])
class UserController {

    @Autowired
    private lateinit var userService: UserService

    @GetMapping("/{id}")
    @ApiOperation("Find by id")
    fun findById(@PathVariable id: String): User? {
        return userService.findById(id)
    }

    @PostMapping
    @ApiOperation("Save")
    fun save(@RequestBody user: User): User {
        return userService.save(user)
    }

    @PostMapping("/attach-role/{userid}/{roleid}")
    @ApiOperation("Attach role")
    fun attachRole(
        @PathVariable userid: String,
        @PathVariable roleid: Int
    ): User {
        return userService.attachRole(roleid, userid)
    }

    @PostMapping("/revoke-role/{userid}/{roleid}")
    @ApiOperation("Revoke role")
    fun revokeRole(
        @PathVariable userid: String,
        @PathVariable roleid: Int
    ): User {
        return userService.revokeRole(roleid, userid)
    }

    @PutMapping("/{id}")
    @ApiOperation("Update")
    fun update(
        @PathVariable id: String,
        @RequestBody user: User
    ): User {
        user.id = id
        return userService.update(user)
    }

    @GetMapping("/list")
    @ApiOperation("List")
    fun list(): List<User> {
        return userService.findAll()
    }

    @GetMapping("/page")
    @ApiOperation("Page")
    fun listPage(pageable: Pageable): Page<User> {
        return userService.findAllPage(pageable)
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete")
    fun delete(@PathVariable id: String) {
        userService.delete(id)
    }
}