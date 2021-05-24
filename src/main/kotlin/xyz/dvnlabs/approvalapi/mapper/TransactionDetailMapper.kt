/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.mapper

import org.mapstruct.*
import xyz.dvnlabs.approvalapi.dto.TransactionDetailDTO
import xyz.dvnlabs.approvalapi.entity.TransactionDetail

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface TransactionDetailMapper : GenericMapper<TransactionDetail, TransactionDetailDTO> {

    override fun asEntity(dto: TransactionDetailDTO?): TransactionDetail

    @InheritInverseConfiguration
    override fun asDTO(entity: TransactionDetail?): TransactionDetailDTO


}