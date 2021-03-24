/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.core.config

import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory

open class AbstractDBConfig {
    lateinit var uri: String

    fun mongoDBFactory(): MongoDatabaseFactory {
        return SimpleMongoClientDatabaseFactory(uri)
    }

    @Bean
    open fun mongoTemplate(): MongoTemplate {
        return MongoTemplate(mongoDBFactory())
    }
}