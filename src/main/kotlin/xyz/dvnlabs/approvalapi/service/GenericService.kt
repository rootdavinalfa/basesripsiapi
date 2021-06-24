/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.query.Query

interface GenericService<E, ID> {

    fun findById(id: ID): E?

    fun save(entity: E): E

    fun update(entity: E): E

    fun findAll(): MutableList<E>

    fun findAllPage(pageable: Pageable): Page<E>

    fun findAllPageWithQuery(pageable: Pageable, query: Query): Page<E> {
        return Page.empty()
    }

    fun findAllWithQuery(query: Query): List<E>{
        return emptyList()
    }

    fun delete(id: ID)

    fun existById(id: ID): Boolean {
        return false
    }
}