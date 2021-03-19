package xyz.dvnlabs.approvalapi.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Employee(
    @Id var idEmployee: String = "",
    var employeeName: String = "",
    var placement: String = "",
    var user: User? = null
)
