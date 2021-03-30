/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.mapper

import org.mapstruct.InheritInverseConfiguration
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy
import xyz.dvnlabs.approvalapi.dto.UserDTO
import xyz.dvnlabs.approvalapi.dto.UserNoPasswordDTO
import xyz.dvnlabs.approvalapi.entity.User

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface UserMapper : GenericMapper<User, UserDTO> {
    override fun asEntity(dto: UserDTO): User

    @InheritInverseConfiguration
    override fun asDTO(entity: User): UserDTO

    fun asUserNoPasswordDTO(entity: User): UserNoPasswordDTO
}
