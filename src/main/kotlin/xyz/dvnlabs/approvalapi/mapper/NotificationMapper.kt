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
import xyz.dvnlabs.approvalapi.dto.NotificationDTO
import xyz.dvnlabs.approvalapi.entity.Notification

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
interface NotificationMapper : GenericMapper<Notification, NotificationDTO> {

    override fun asDTO(entity: Notification?): NotificationDTO

    @InheritInverseConfiguration
    override fun asEntity(dto: NotificationDTO?): Notification

}