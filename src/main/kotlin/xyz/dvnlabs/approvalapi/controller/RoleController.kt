package xyz.dvnlabs.approvalapi.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import xyz.dvnlabs.approvalapi.entity.Role
import xyz.dvnlabs.approvalapi.service.RoleService

@RequestMapping("/role")
@RestController
@Api(tags = ["Role Controller"])
class RoleController {

    @Autowired
    private lateinit var roleService: RoleService

    @GetMapping("/{id}")
    @ApiOperation("Find by id")
    fun findById(@PathVariable id: Int): Role? {
        return roleService.findById(id)
    }

    @PostMapping
    @ApiOperation("Save")
    fun save(@RequestBody user: Role): Role {
        return roleService.save(user)
    }

    @PutMapping("/{id}")
    @ApiOperation("Update")
    fun update(
        @PathVariable id: Int,
        @RequestBody user: Role
    ): Role {
        user.id = id
        return roleService.update(user)
    }

    @GetMapping("/list")
    @ApiOperation("List")
    fun list(): List<Role> {
        return roleService.findAll()
    }

    @GetMapping("/page")
    @ApiOperation("Page")
    fun listPage(pageable: Pageable): Page<Role> {
        return roleService.findAllPage(pageable)
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete")
    fun delete(@PathVariable id: Int) {
        roleService.delete(id)
    }

}