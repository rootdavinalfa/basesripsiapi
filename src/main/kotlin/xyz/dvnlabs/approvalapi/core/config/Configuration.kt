/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.core.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "primary.config")
class Configuration {

    var expiredDay : Long = 1

    var tokenSecret : String = "c2F5YXN1a2FtYW50YXBtYW50YXB0YXBpdGFrdXRkb3Nh"

}