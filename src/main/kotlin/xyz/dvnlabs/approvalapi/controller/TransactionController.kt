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
import xyz.dvnlabs.approvalapi.core.specification.QueryHelper
import xyz.dvnlabs.approvalapi.core.specification.QueryOperation
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
    fun list(
        @RequestParam(defaultValue = "") statusFlag: String,
        @RequestParam(defaultValue = "") userRequest: String,
        @RequestParam(defaultValue = "") userApprove: String,
        @RequestParam(defaultValue = "") userDelivery: String,
        @RequestParam(defaultValue = "") statusFlagIn: String
    ): List<TransactionDTO> {
        val queryHelper = QueryHelper()
            .addOne("statusFlag", statusFlag, QueryOperation.EQUAL)
            .addOne("userRequest", userRequest, QueryOperation.EQUAL)
            .addOne("userApprove", userApprove, QueryOperation.EQUAL)
            .addOne("userDelivery", userDelivery, QueryOperation.EQUAL)

        return transactionMapper.asDTOList(
            transactionService
                .findAllWithQuery(
                    queryHelper.or()
                        .buildQuery()
                )
        )
    }


    @GetMapping("/page")
    @ApiOperation("Page")
    fun listPage(
        pageable: Pageable,
        @RequestParam(defaultValue = "") statusFlag: String,
        @RequestParam(defaultValue = "") userRequest: String,
        @RequestParam(defaultValue = "") userApprove: String,
        @RequestParam(defaultValue = "") userDelivery: String,
        @RequestParam(defaultValue = "") statusFlagIn: String
    ): Page<TransactionDTO> {
        val queryHelper = QueryHelper()
            .addOne("statusFlag", statusFlag, QueryOperation.EQUAL)
            .addOne("userRequest", userRequest, QueryOperation.EQUAL)
            .addOne("userApprove", userApprove, QueryOperation.EQUAL)
            .addOne("userDelivery", userDelivery, QueryOperation.EQUAL)

        if (statusFlagIn.isNotEmpty()) {
            queryHelper.addOne(
                "statusFlag",
                statusFlagIn.split(","),
                QueryOperation.EQUAL_IN
            )
        }

        return transactionService.findAllPageWithQuery(
            pageable,
            queryHelper
                .or()
                .buildQuery()
        ).map {
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

    @PostMapping("/create")
    @ApiOperation("Create Transaction")
    fun createTransaction(
        @RequestBody request: RequestTransactionDTO
    ): TransactionDTO {
        val transaction = transactionMapper.asEntity(request.transactionDTO)
        val transactionDetail = transactionDetailMapper.asEntityList(request.transactionDetails)
        return transactionMapper.asDTO(transactionService.createTransaction(transaction, transactionDetail))
    }

    @PutMapping("/validate")
    @ApiOperation("Validate Transaction")
    fun validateTransaction(
        @RequestBody request: RequestTransactionDTO
    ): TransactionDTO {
        val transaction = transactionMapper.asEntity(request.transactionDTO)
        val transactionDetail = transactionDetailMapper.asEntityList(request.transactionDetails)
        return transactionMapper
            .asDTO(
                transactionService.validationTransaction(transaction, transactionDetail)
            )
    }

    @PutMapping("/cancel/{idTransaction}")
    @ApiOperation("Cancel Transaction")
    fun cancelTransaction(
        @PathVariable idTransaction: Long
    ): TransactionDTO {
        return transactionMapper
            .asDTO(transactionService.validateCancelTransaction(idTransaction))
    }

    @PutMapping("/attach-delivery/{idTransaction}/{iduser}")
    @ApiOperation("Attach delivery Transaction")
    fun attachDeliveryTransaction(
        @PathVariable idTransaction: Long,
        @PathVariable iduser: String
    ): TransactionDTO {
        return transactionMapper
            .asDTO(
                transactionService.attachDelivery(idTransaction, iduser)
            )
    }

}