/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.service

import xyz.dvnlabs.approvalapi.entity.Employee

interface EmployeeService : GenericService<Employee, String> {

    fun getEmployeeByNameOrPlacement(name: String, placement: String): List<Employee>?

    fun attachUser(id: String, userName: String, employeeID: String): Employee

    fun detachUser(id: String, userName: String, employeeID: String): Employee
}