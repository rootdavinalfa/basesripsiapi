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
import xyz.dvnlabs.approvalapi.dto.RequestTransactionDTO
import xyz.dvnlabs.approvalapi.dto.TransactionDTO
import xyz.dvnlabs.approvalapi.mapper.TransactionDetailMapper
import xyz.dvnlabs.approvalapi.mapper.TransactionMapper
import xyz.dvnlabs.approvalapi.service.TransactionService

@RequestMapping("/transaction")
@RestController
@Api(tags = ["Transaction Controller"])
class TransactionController {

    @Autowired
    private lateinit var transactionService: TransactionService

    @Autowired
    private lateinit var transactionMapper: TransactionMapper

    @Autowired
    private lateinit var transactionDetailMapper: TransactionDetailMapper

    @GetMapping("/{id}")
    @ApiOperation("Find by id")
    fun findById(@PathVariable id: Long): TransactionDTO? {
        return transactionMapper.asDTO(transactionService.findById(id))
    }

    @PostMapping
    @ApiOperation("Save")
    fun save(
        @RequestBody units: TransactionDTO
    ): TransactionDTO {
        val emp = transactionMapper.asEntity(units)
        return transactionMapper.asDTO(transactionService.save(emp))
    }


    @PutMapping("/{id}")
    @ApiOperation("Update")
    fun update(
        @PathVariable id: Long,
        @RequestBody units: TransactionDTO
    ): TransactionDTO {
        units.idTransaction = id
        val emp = transactionMapper.asEntity(units)
        return transactionMapper.asDTO(transactionService.update(emp))
    }


    @GetMapping("/list")
    @ApiOperation("List")
    fun list(): List<TransactionDTO> {
        return transactionMapper.asDTOList(transactionService.findAll())
    }


    @GetMapping("/page")
    @ApiOperation("Page")
    fun listPage(pageable: Pageable): Page<TransactionDTO> {
        return transactionService.findAllPage(pageable).map {
            return@map transactionMapper.asDTO(it)
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete")
    fun delete(@PathVariable id: Long) {
        transactionService.delete(id)
    }

    @PutMapping("/attach/{idtrx}/{iddetail}")
    @ApiOperation("Attach detail")
    fun attachDetail(
        @PathVariable idtrx: Long,
        @PathVariable iddetail: String
    ): TransactionDTO {
        return transactionMapper.asDTO(transactionService.attachDetail(idtrx, iddetail))
    }

    @PutMapping("/deattach/{idtrx}/{iddetail}")
    @ApiOperation("deAttach detail")
    fun deAttachDetail(
        @PathVariable idtrx: Long,
        @PathVariable iddetail: String
    ): TransactionDTO {
        return transactionMapper.asDTO(transactionService.deAttachDetail(idtrx, iddetail))
    }

    @PostMapping("/create/transaction")
    @ApiOperation("Create Transaction")
    fun createTransaction(
        @RequestBody request: RequestTransactionDTO
    ): TransactionDTO {
        val transaction = transactionMapper.asEntity(request.transactionDTO)
        val transactionDetail = transactionDetailMapper.asEntityList(request.transactionDetails)
        return transactionMapper.asDTO(transactionService.createTransaction(transaction, transactionDetail))
    }

}