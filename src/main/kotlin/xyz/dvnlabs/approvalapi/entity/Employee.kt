/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Employee(
    @Id var idEmployee: String = "",
    var employeeName: String = "",
    var placement: String = "",
    @DBRef(lazy = true)
    var user: User? = null
)
