/*
 * Copyright (c) 2021. 
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */
package xyz.dvnlabs.approvalapi.dao

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.stereotype.Repository

@Repository
interface SpecificationRepository<T> {
    /**
     *
     */
    fun findAllQuery(query: Query, clazz: Class<T>): List<T>

    /**
     *
     */
    fun findAllQueryPage(query: Query, pageable: Pageable, clazz: Class<T>): Page<T>

    /**
     *
     */
    fun findAllQuerySort(query: Query, sort: Sort, clazz: Class<T>): List<T>
}