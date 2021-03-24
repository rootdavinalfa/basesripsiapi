/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.core.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.mongodb.core.MongoTemplate

@Configuration
@ConfigurationProperties(prefix = "primary.db")
class DBConfiguration : AbstractDBConfig() {

    @Primary
    @Bean
    override fun mongoTemplate(): MongoTemplate {
        return MongoTemplate(mongoDBFactory())
    }

}