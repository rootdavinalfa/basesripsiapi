/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.dao

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.repository.NoRepositoryBean
import java.util.*

@NoRepositoryBean
interface GenericDAO<E, ID> : MongoRepository<E, ID> {

    override fun findById(id: ID): Optional<E>

    override fun <S : E> save(entity: S): S


    override fun findAll(): MutableList<E>

    override fun findAll(pageable: Pageable): Page<E>

    override fun deleteById(id: ID)

}