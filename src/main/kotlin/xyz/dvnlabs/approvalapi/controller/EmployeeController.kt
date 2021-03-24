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
import xyz.dvnlabs.approvalapi.core.exception.InvalidRequestException
import xyz.dvnlabs.approvalapi.entity.Employee
import xyz.dvnlabs.approvalapi.service.EmployeeService

@RequestMapping("/employee")
@RestController
@Api(tags = ["Employee Controller"])
class EmployeeController {

    @Autowired
    private lateinit var employeeService: EmployeeService

    @GetMapping("/{id}")
    @ApiOperation("Find by id")
    fun findById(@PathVariable id: String): Employee? {
        return employeeService.findById(id)
    }

    @PostMapping
    @ApiOperation("Save")
    fun save(@RequestBody employee: Employee): Employee {
        return employeeService.save(employee)
    }


    @PutMapping("/{id}")
    @ApiOperation("Update")
    fun update(
        @PathVariable id: String,
        @RequestBody employee: Employee
    ): Employee {
        employee.idEmployee = id
        return employeeService.update(employee)
    }

    @PutMapping("/user/attach/{id}")
    @ApiOperation("Attach user")
    fun userAttach(
        @PathVariable id: String,
        @RequestParam(defaultValue = "") idUser: String,
        @RequestParam(defaultValue = "") userName: String
    ): Employee {
        if (idUser.isEmpty() && userName.isEmpty()) {
            throw InvalidRequestException("idUser or userName must have a value!")
        }
        return employeeService.attachUser(idUser, userName, id)
    }

    @PutMapping("/user/dettach/{id}")
    @ApiOperation("Dettach user")
    fun userDettach(
        @PathVariable id: String,
        @RequestParam(defaultValue = "") idUser: String,
        @RequestParam(defaultValue = "") userName: String
    ): Employee {
        if (idUser.isEmpty() && userName.isEmpty()) {
            throw InvalidRequestException("idUser or userName must have a value!")
        }
        return employeeService.detachUser(idUser, userName, id)
    }

    @GetMapping("/list")
    @ApiOperation("List")
    fun list(): List<Employee> {
        return employeeService.findAll()
    }

    @GetMapping("/list/search")
    @ApiOperation("List")
    fun listSearch(
        @RequestParam(defaultValue = "") name: String,
        @RequestParam(defaultValue = "") placement: String
    ): List<Employee>? {
        return employeeService.getEmployeeByNameOrPlacement(name, placement)
    }

    @GetMapping("/page")
    @ApiOperation("Page")
    fun listPage(pageable: Pageable): Page<Employee> {
        return employeeService.findAllPage(pageable)
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete")
    fun delete(@PathVariable id: String) {
        employeeService.delete(id)
    }

}