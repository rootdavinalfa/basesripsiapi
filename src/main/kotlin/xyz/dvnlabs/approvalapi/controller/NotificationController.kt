/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import xyz.dvnlabs.approvalapi.core.exception.InvalidRequestException
import xyz.dvnlabs.approvalapi.core.specification.QueryHelper
import xyz.dvnlabs.approvalapi.core.specification.QueryOperation
import xyz.dvnlabs.approvalapi.dto.NotificationDTO
import xyz.dvnlabs.approvalapi.mapper.NotificationMapper
import xyz.dvnlabs.approvalapi.service.NotificationService

@RequestMapping("/notification")
@RestController
@Api(tags = ["Notification Controller"])
class NotificationController {

    @Autowired
    private lateinit var notificationService: NotificationService

    @Autowired
    private lateinit var notificationMapper: NotificationMapper

    @GetMapping("/{id}")
    @ApiOperation("Find by id")
    fun findById(@PathVariable id: String): NotificationDTO? {
        return notificationMapper.asDTO(notificationService.findById(id))
    }

    @PostMapping
    @ApiOperation("Save")
    fun save(
        @RequestBody units: NotificationDTO
    ): NotificationDTO {
        val emp = notificationMapper.asEntity(units)
        return notificationMapper.asDTO(notificationService.save(emp))
    }


    @PutMapping("/{id}")
    @ApiOperation("Update")
    fun update(
        @PathVariable id: String,
        @RequestBody units: NotificationDTO
    ): NotificationDTO {
        units.id = id
        val emp = notificationMapper.asEntity(units)
        return notificationMapper.asDTO(notificationService.update(emp))
    }


    @GetMapping("/list")
    @ApiOperation("List")
    fun list(): List<NotificationDTO> {
        return notificationMapper.asDTOList(notificationService.findAll())
    }


    @GetMapping("/page")
    @ApiOperation("Page")
    fun listPage(
        pageable: Pageable,
        @RequestParam(defaultValue = "") target: String
    ): Page<NotificationDTO> {
        return notificationService.findAllPageWithQuery(
            pageable, QueryHelper()
                .addOne("target", target, QueryOperation.EQUAL)
                .and()
                .buildQuery()
        ).map {
            return@map notificationMapper.asDTO(it)
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete")
    fun delete(@PathVariable id: String) {
        notificationService.delete(id)
    }

    @PutMapping("/attach/{id}")
    @ApiOperation("Update")
    fun attachTransaction(
        @PathVariable id: String,
        @RequestParam(required = false) idTransaction: Long?
    ): NotificationDTO {
        if (idTransaction == null) {
            throw InvalidRequestException("Id transaction is required!")
        }
        return notificationMapper.asDTO(notificationService.attachTransaction(idTransaction, id))
    }

    @PutMapping("/detach/{id}")
    @ApiOperation("Update")
    fun detachTransaction(
        @PathVariable id: String
    ): NotificationDTO {

        return notificationMapper.asDTO(notificationService.detachTransaction(id))
    }

}