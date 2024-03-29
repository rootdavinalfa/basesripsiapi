/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.dvnlabs.approvalapi.core.exception.ResourceExistsException
import xyz.dvnlabs.approvalapi.core.exception.ResourceNotFoundException
import xyz.dvnlabs.approvalapi.core.helper.CommonHelper
import xyz.dvnlabs.approvalapi.core.helper.CopyNonNullProperties
import xyz.dvnlabs.approvalapi.core.specification.QueryHelper
import xyz.dvnlabs.approvalapi.core.specification.QueryOperation
import xyz.dvnlabs.approvalapi.dao.EmployeeDAO
import xyz.dvnlabs.approvalapi.dao.UserDAO
import xyz.dvnlabs.approvalapi.entity.Employee
import xyz.dvnlabs.approvalapi.service.EmployeeService
import java.util.*

@Service
@Transactional(rollbackFor = [Exception::class])
class EmployeeServices : EmployeeService {

    @Autowired
    private lateinit var employeeDAO: EmployeeDAO

    @Autowired
    private lateinit var userDAO: UserDAO

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    override fun findById(id: String): Employee? {
        return employeeDAO.findById(id)
            .orElseThrow { ResourceNotFoundException("Employee with ID: $id not found!") }
    }

    override fun save(entity: Employee): Employee {

        val max = employeeDAO.firstIDDesc()

        entity.idEmployee = CommonHelper.getStringSeq(max?.let {
            return@let it
        } ?: kotlin.run {
            return@run "XXXX000"
        }, Date(), "", "", 3, true, "yyMM")

        if (employeeDAO.existsById(entity.idEmployee)) {
            throw ResourceExistsException("Employee with ID: ${entity.idEmployee} is exist!")
        }

        return employeeDAO.save(entity)
    }

    override fun update(entity: Employee): Employee {
        return employeeDAO.findById(entity.idEmployee)
            .map {
                CopyNonNullProperties.copyNonNullProperties(entity, it)
                return@map employeeDAO.save(it)
            }
            .orElseThrow {
                ResourceNotFoundException("Employee not found")
            }
    }

    override fun findAll(): MutableList<Employee> {
        return employeeDAO.findAll()
    }

    override fun findAllPage(pageable: Pageable): Page<Employee> {
        return employeeDAO.findAll(pageable)
    }

    override fun delete(id: String) {
        return employeeDAO.findById(id).map { rowExist ->
            employeeDAO.deleteById(rowExist.idEmployee)
        }.orElseThrow { ResourceNotFoundException("Employee data not found!") }
    }

    override fun getEmployeeByNameOrPlacement(name: String, placement: String): List<Employee>? {

        return employeeDAO.findAllQuery(
            QueryHelper()
                .addOne("employeeName", name, QueryOperation.MATCH_ANY)
                .addOne("placement", placement, QueryOperation.MATCH_ANY)
                .or()
                .buildQuery(),
            Employee::class.java
        )

    }

    override fun findAllPageWithQuery(pageable: Pageable, query: Query): Page<Employee> {
        return employeeDAO.findAllQueryPage(
            query,
            pageable,
            Employee::class.java
        )
    }

    override fun attachUser(id: String, userName: String, employeeID: String): Employee {
        return userDAO.findByUserNameOrId(userName, id)?.let {
            val employee = employeeDAO.findById(employeeID)
                .orElseThrow { ResourceNotFoundException("Employee not found") }
            employee.user = it
            return@let update(employee)
        } ?: kotlin.run {
            throw ResourceNotFoundException("User not found!")
        }
    }

    override fun detachUser(id: String, userName: String, employeeID: String): Employee {
        val employee = employeeDAO.findById(employeeID)
            .orElseThrow { ResourceNotFoundException("Employee not found") }
        employee.user = null
        return update(employee)
    }
}