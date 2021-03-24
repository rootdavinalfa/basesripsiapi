/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface GenericService<E,ID> {

    fun findById(id : ID) : E?

    fun save(entity : E) : E

    fun update(entity : E) : E

    fun findAll() : MutableList<E>

    fun findAllPage(pageable : Pageable) : Page<E>

    fun delete(id : ID)
}