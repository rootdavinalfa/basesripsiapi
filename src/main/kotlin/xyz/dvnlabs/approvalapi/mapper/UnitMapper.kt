/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.mapper

import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import xyz.dvnlabs.approvalapi.dto.UnitDTO
import xyz.dvnlabs.approvalapi.entity.Unit

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UnitMapper : GenericMapper<Unit, UnitDTO> {
    override fun asEntity(dto: UnitDTO?): Unit

    @InheritInverseConfiguration
    override fun asDTO(entity: Unit?): UnitDTO

}
