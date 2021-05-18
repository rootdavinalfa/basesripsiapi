/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.mapper

import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import xyz.dvnlabs.approvalapi.dto.TransactionDTO
import xyz.dvnlabs.approvalapi.entity.Transaction

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface TransactionMapper : GenericMapper<Transaction, TransactionDTO> {
    override fun asDTO(entity: Transaction?): TransactionDTO

    @InheritInverseConfiguration
    override fun asEntity(dto: TransactionDTO?): Transaction
}