/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.mapper

interface GenericMapper<E, DTO> {
    fun asEntity(dto: DTO): E

    fun asDTO(entity: E): DTO

    fun asEntityList(dtoList: List<DTO>): List<E>

    fun asDTOList(entityList: List<E>): List<DTO>
}