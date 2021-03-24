/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.util.*

@Document
data class User(
    @Id var id: String = "",
    @Indexed(unique = true)
    @Field
    var email: String = "",
    @Indexed(unique = true)
    @Field
    var userName: String = "",
    @Field
    var password: String = "",
    @Field
    var registeredOn: Date = Date(),
    @Field
    var roles: List<Role>? = null
)
