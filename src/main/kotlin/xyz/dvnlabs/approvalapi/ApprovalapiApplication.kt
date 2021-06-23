/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@SpringBootApplication
@EnableMongoRepositories(
    basePackages = ["xyz.dvnlabs.approvalapi.dao"]
)
class ApprovalapiApplication

fun main(args: Array<String>) {
    runApplication<ApprovalapiApplication>(*args)
}
