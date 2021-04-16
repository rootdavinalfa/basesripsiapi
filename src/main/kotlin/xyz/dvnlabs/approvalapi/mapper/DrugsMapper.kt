/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.mapper

import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import xyz.dvnlabs.approvalapi.dto.DrugsDTO
import xyz.dvnlabs.approvalapi.entity.Drugs

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface DrugsMapper : GenericMapper<Drugs, DrugsDTO> {

    override fun asEntity(dto: DrugsDTO?): Drugs

    @InheritInverseConfiguration
    override fun asDTO(entity: Drugs?): DrugsDTO
}