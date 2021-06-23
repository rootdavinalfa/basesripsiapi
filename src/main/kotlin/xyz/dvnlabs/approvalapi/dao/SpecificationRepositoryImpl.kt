/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Repository

@Repository
class SpecificationRepositoryImpl<T> : SpecificationRepository<T> {

    @Autowired
    private lateinit var mongoTemplate: MongoTemplate

    override fun findAllQuery(query: Query, clazz: Class<T>): MutableList<T> {
        return mongoTemplate
            .find(query, clazz)
    }

    override fun findAllQueryPage(query: Query, pageable: Pageable, clazz: Class<T>): Page<T> {
        query.with(pageable)

        val filtered = mongoTemplate.find(query, clazz)

        return PageableExecutionUtils.getPage(
            filtered,
            pageable
        ) { mongoTemplate.count(query, clazz) }
    }


    override fun findAllQuerySort(query: Query, sort: Sort, clazz: Class<T>): MutableList<T> {
        query.with(sort)

        return mongoTemplate.find(query, clazz)

    }

}