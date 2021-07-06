/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.service.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import xyz.dvnlabs.approvalapi.core.bus.RxBus
import xyz.dvnlabs.approvalapi.core.exception.InvalidRequestException
import xyz.dvnlabs.approvalapi.core.exception.ResourceExistsException
import xyz.dvnlabs.approvalapi.core.exception.ResourceNotFoundException
import xyz.dvnlabs.approvalapi.core.helper.CopyNonNullProperties
import xyz.dvnlabs.approvalapi.core.helper.GlobalContext
import xyz.dvnlabs.approvalapi.core.specification.QueryHelper
import xyz.dvnlabs.approvalapi.core.specification.QueryOperation
import xyz.dvnlabs.approvalapi.dao.TransactionDAO
import xyz.dvnlabs.approvalapi.dto.NotificationSenderDTO
import xyz.dvnlabs.approvalapi.dto.TargetKind
import xyz.dvnlabs.approvalapi.entity.Role
import xyz.dvnlabs.approvalapi.entity.Transaction
import xyz.dvnlabs.approvalapi.entity.TransactionDetail
import xyz.dvnlabs.approvalapi.service.*

@Service
@Transactional(rollbackFor = [Exception::class])
class TransactionServices : TransactionService {

    @Autowired
    private lateinit var transactionDAO: TransactionDAO

    @Autowired
    private lateinit var transactionDetailService: TransactionDetailService

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var roleService: RoleService

    @Autowired
    private lateinit var drugService: DrugsService

    override fun findById(id: Long): Transaction? {
        return transactionDAO
            .findById(id)
            .orElseThrow {
                ResourceNotFoundException("Transaction not found with id $id")
            }
    }

    override fun save(entity: Transaction): Transaction {
        val max = transactionDAO.firstIDDesc()

        entity.idTransaction = max?.let {
            return@let it + 1
        } ?: kotlin.run {
            return@run 1
        }


        if (transactionDAO.existsById(entity.idTransaction)) {
            throw ResourceExistsException("Data exists!")
        }

        return transactionDAO.save(entity)
    }

    override fun update(entity: Transaction): Transaction {
        return transactionDAO.findById(entity.idTransaction)
            .map {
                CopyNonNullProperties.copyNonNullProperties(entity, it)
                return@map transactionDAO.save(it)
            }
            .orElseThrow {
                ResourceNotFoundException("Transaction not found")
            }
    }

    override fun findAll(): MutableList<Transaction> {
        return transactionDAO.findAll()
    }

    override fun findAllWithQuery(query: Query): List<Transaction> {
        return transactionDAO.findAllQuery(query, Transaction::class.java)
    }

    override fun findAllPage(pageable: Pageable): Page<Transaction> {
        return transactionDAO.findAll(pageable)
    }

    override fun findAllPageWithQuery(pageable: Pageable, query: Query): Page<Transaction> {
        return transactionDAO.findAllQueryPage(query, pageable, Transaction::class.java)
    }

    override fun delete(id: Long) {
        transactionDAO.findById(id)
            .map {
                return@map transactionDAO.deleteById(it.idTransaction)
            }
            .orElseThrow {
                ResourceNotFoundException("Transaction not found")
            }
    }

    override fun attachDetail(idTransaction: Long, idDetail: String): Transaction? {
        val transaction = findById(idTransaction)
        val detail = transactionDetailService.findById(idDetail)
        transaction?.let {
            if (it.transactionDetails.isNullOrEmpty()) {
                it.transactionDetails = mutableListOf()

                detail?.let { det ->
                    it.transactionDetails?.add(det)
                }

            } else {
                detail?.let { det ->
                    it.transactionDetails?.add(det)
                }
            }
        }

        transaction?.let {
            update(transaction)
        }

        return transaction
    }

    override fun deAttachDetail(idTransaction: Long, idDetail: String): Transaction? {
        val transaction = findById(idTransaction)
        val detail = transactionDetailService.findById(idDetail)
        transaction?.let {
            if (it.transactionDetails.isNullOrEmpty()) {
                it.transactionDetails = mutableListOf()

                detail?.let { det ->
                    it.transactionDetails?.remove(det)
                }

            } else {
                detail?.let { det ->
                    it.transactionDetails?.remove(det)
                }
            }
        }

        transaction?.let {
            update(transaction)
        }

        return transaction
    }

    override fun createTransaction(
        transactionDTO: Transaction,
        transactionDetails: List<TransactionDetail>?
    ): Transaction? {
        println("Transaction Begin")
        transactionDTO.userRequest = GlobalContext.getUsername()
        transactionDetails?.forEach {
            transactionDTO.transactionDetails = mutableListOf()
            transactionDTO.transactionDetails?.add(transactionDetailService.save(it))
            println(transactionDTO.transactionDetails)
        }
        val transaction = save(transactionDTO)

        RxBus
            .publish(
                NotificationSenderDTO(
                    userNameSender = GlobalContext.getUsername(),
                    target = "ROLE_VGUDANG",
                    targetKind = TargetKind.ROLE,
                    title = "Permintaan Barang Baru",
                    body = "Ada permintaan barang baru oleh: ${GlobalContext.getUsername()} silahkan dicek!",
                    data = transaction
                )
            )
        return transaction
    }

    override fun validationTransaction(
        transactionDTO: Transaction,
        transactionDetails: List<TransactionDetail>?
    ): Transaction? {
        return roleService.getRoleByName("ROLE_VGUDANG")
            ?.let { role ->

                val user = userService.findAllWithQuery(
                    QueryHelper()
                        .addOne("roles", Role(id = role.id), QueryOperation.EQUAL)
                        .and()
                        .buildQuery()
                ).find { usr -> usr.userName == GlobalContext.getUsername() }
                    ?: throw InvalidRequestException("User login role not qualified, user must have ${role.roleName}")

                var transaction = findById(transactionDTO.idTransaction)!!

                if (transaction.statusFlag == "2") {
                    throw InvalidRequestException("Data sudah di validasi")
                }

                transaction.statusFlag = "2"
                transaction.userApprove = user.userName


                transaction.transactionDetails?.forEach {
                    it.drug?.let { drug ->
                        val drugs = drugService.findById(drug.idDrug)!!
                        //Correction from detail
                        val detail = transactionDetails?.first { details -> details.id == it.id }
                        val newQty = detail?.qty ?: 0.0

                        if (newQty > it.qty) {
                            throw InvalidRequestException("Perubahan tidak boleh lebih besar dari permintaan sebelumnya")
                        }

                        if (newQty == 0.0) {
                            it.realQty = it.qty
                            drugs.qty = drugs.qty - it.qty
                        } else {
                            it.realQty = newQty
                            drugs.qty = drugs.qty - newQty
                        }
                        transactionDetailService.update(it)
                        drugService.update(drugs)
                    }
                }
                transaction = update(transaction)
                RxBus
                    .publish(
                        NotificationSenderDTO(
                            userNameSender = GlobalContext.getUsername(),
                            target = transaction.userRequest!!,
                            targetKind = TargetKind.INDIVIDUAL,
                            title = "Permintaan sudah divalidasi",
                            body = "Permintaan untuk ID:${transaction.idTransaction} sudah divalidasi oleh ${transaction.userApprove}",
                            data = transaction
                        )
                    )

                return@let transaction

            }
    }

    override fun attachDelivery(idTransaction: Long, idUser: String): Transaction? {
        return roleService.getRoleByName("ROLE_VGUDANG")
            ?.let { role ->
                val user = userService.findAllWithQuery(
                    QueryHelper()
                        .addOne("roles", Role(id = role.id), QueryOperation.EQUAL)
                        .and()
                        .buildQuery()
                ).find { usr -> usr.userName == GlobalContext.getUsername() }
                    ?: throw InvalidRequestException("User login role not qualified, user must have ${role.roleName}")

                var transaction = findById(idTransaction)
                transaction?.statusFlag?.let {
                    if (it != "2")
                        throw InvalidRequestException("Transaction not validated, please validate first before attach delivery")
                }
                transaction?.statusFlag = "3"
                transaction?.userDelivery = user.userName

                if (transaction != null) {
                    transaction = update(transaction)
                    RxBus
                        .publish(
                            NotificationSenderDTO(
                                userNameSender = GlobalContext.getUsername(),
                                target = transaction.userDelivery!!,
                                targetKind = TargetKind.INDIVIDUAL,
                                title = "Silahkan antar permintaan barang berikut",
                                body = "Permintaan untuk ID:${transaction.idTransaction} harus diantar ke: ${transaction.userRequest}",
                                data = transaction
                            )
                        )

                    return@let transaction
                }
                return@let null

            }
    }

    override fun delivered(idTransaction: Long, message: String): Transaction? {
        return roleService.getRoleByName("ROLE_APOTIK")
            ?.let { role ->
                val user = userService.findAllWithQuery(
                    QueryHelper()
                        .addOne("roles", Role(id = role.id), QueryOperation.EQUAL)
                        .and()
                        .buildQuery()
                ).find { usr -> usr.userName == GlobalContext.getUsername() }
                    ?: throw InvalidRequestException("User login role not qualified, user must have ${role.roleName}")

                var transaction = findById(idTransaction)
                transaction?.statusFlag?.let {
                    if (it != "3")
                        throw InvalidRequestException("Transaction not on delivery")
                }
                transaction?.statusFlag = "4"
                transaction?.userDelivery = user.userName

                if (transaction != null) {
                    transaction = update(transaction)
                    RxBus
                        .publish(
                            NotificationSenderDTO(
                                userNameSender = GlobalContext.getUsername(),
                                target = transaction.userDelivery!!,
                                targetKind = TargetKind.INDIVIDUAL,
                                title = "Permintaan barang sudah diterima",
                                body = "Permintaan untuk ID:${transaction.idTransaction} sudah diterima oleh: ${transaction.userRequest}",
                                data = transaction
                            )
                        )

                    RxBus
                        .publish(
                            NotificationSenderDTO(
                                userNameSender = GlobalContext.getUsername(),
                                target = transaction.userApprove!!,
                                targetKind = TargetKind.INDIVIDUAL,
                                title = "Permintaan barang sudah diterima",
                                body = "Permintaan untuk ID:${transaction.idTransaction} sudah diterima oleh:: ${transaction.userRequest}",
                                data = transaction
                            )
                        )

                    return@let transaction
                }
                return@let null

            }
    }

    override fun validateCancelTransaction(idTransaction: Long): Transaction? {
        return findById(idTransaction)?.let {
            if (it.userRequest != GlobalContext.getUsername()) {
                throw InvalidRequestException("Validasi Cancel hanya dapat dilakukan oleh user ${it.userRequest}")
            }

            if (it.statusFlag == "4") {
                throw InvalidRequestException("Cancel tidak dapat dilakukan pada barang yang sudah diterima")
            }

            it.transactionDetails?.forEach {
                it.drug?.let { drug ->
                    val drugs = drugService.findById(drug.idDrug)!!
                    drugs.qty = drugs.qty + it.realQty
                    drugService.update(drugs)
                }
            }

            it.statusFlag = "5"
            return@let update(it)
        }
    }

}