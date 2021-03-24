/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.dvnlabs.approvalapi.entity.Test

@RequestMapping
@RestController
@Api(tags = ["Test Controller"])
class TestController {

    @GetMapping("/hello")
    @ApiOperation("HELLO")
    fun hello(): Test {
        return Test(name = "HELLO")
    }


    @PreAuthorize("hasRole('SUPER')")
    @GetMapping("/super")
    @ApiOperation("SUPER")
    fun supers(): Test {
        return Test(name = "SUPER")
    }

    @PreAuthorize("hasRole('TEST')")
    @GetMapping("/test")
    @ApiOperation("TEST")
    fun textRole(): Test {
        return Test(name = "TEST")
    }

}