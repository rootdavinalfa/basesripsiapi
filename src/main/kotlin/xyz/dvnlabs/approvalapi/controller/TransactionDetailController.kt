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
import xyz.dvnlabs.approvalapi.dto.TransactionDetailDTO
import xyz.dvnlabs.approvalapi.mapper.TransactionDetailMapper
import xyz.dvnlabs.approvalapi.service.TransactionDetailService

@RequestMapping("/transaction-detail")
@RestController
@Api(tags = ["Transaction Detail Controller"])
class TransactionDetailController {

    @Autowired
    private lateinit var transactionDetailService: TransactionDetailService

    @Autowired
    private lateinit var transactionDetailMapper: TransactionDetailMapper

    @GetMapping("/{idtrans}")
    @ApiOperation("Find by id")
    fun findById(
        @PathVariable idtrans: String
    ): TransactionDetailDTO? {
        return transactionDetailMapper.asDTO(
            transactionDetailService.findById(
                idtrans
            )
        )
    }

    @PostMapping
    @ApiOperation("Save")
    fun save(
        @RequestBody units: TransactionDetailDTO
    ): TransactionDetailDTO {
        val emp = transactionDetailMapper.asEntity(units)
        return transactionDetailMapper.asDTO(transactionDetailService.save(emp))
    }


    @PutMapping("/{idtrans}")
    @ApiOperation("Update")
    fun update(
        @PathVariable idtrans: String,
        @RequestBody units: TransactionDetailDTO
    ): TransactionDetailDTO {
        units.id = idtrans
        val emp = transactionDetailMapper.asEntity(units)
        return transactionDetailMapper.asDTO(transactionDetailService.update(emp))
    }


    @GetMapping("/list")
    @ApiOperation("List")
    fun list(): List<TransactionDetailDTO> {
        return transactionDetailMapper.asDTOList(transactionDetailService.findAll())
    }


    @GetMapping("/page")
    @ApiOperation("Page")
    fun listPage(pageable: Pageable): Page<TransactionDetailDTO> {
        return transactionDetailService.findAllPage(pageable).map {
            return@map transactionDetailMapper.asDTO(it)
        }
    }

    @DeleteMapping("/{idtrans}")
    @ApiOperation("Delete")
    fun delete(
        @PathVariable idtrans: String
    ) {
        transactionDetailService.delete(idtrans)
    }

}