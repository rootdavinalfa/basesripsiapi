/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API
 */

package xyz.dvnlabs.approvalapi.core.specification

import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import xyz.dvnlabs.approvalapi.core.helper.isList
import xyz.dvnlabs.approvalapi.core.helper.isNull
import xyz.dvnlabs.approvalapi.core.helper.isString

/**
 * ## QueryHelper
 * Class for helping you building criteria using Spring Data MongoDB criteria
 *
 * See Also:
 * - [addOne]
 * - [addCriteria]
 * - [and]
 * - [or]
 * - [build]
 * - [buildQuery]
 */
class QueryHelper {

    var criteriaChain: MutableList<Criteria> = ArrayList()
    var criteria = Criteria()

    /**
     * ## addOne()
     * Function for add one parameter based on parameter
     *
     *
     * [field] The field name specified on @Document (Entity) you want to filter, eg: userName
     *
     * [value] The value you want to provide and compared
     *
     * [operation] The QueryOperation, See [QueryOperation] for more information
     *
     * [required] Specify is filter required or not, if true then the [value] you entered must not be null
     * or something that not the default value of an object
     *
     */
    fun addOne(
        field: String,
        value: Any?,
        operation: QueryOperation,
        required: Boolean = true
    ): QueryHelper {
        if (required && value.isNull()) {
            println("QueryHelper:: Specified value is null and the required flag is true")
            return this
        }

        when (operation) {

            QueryOperation.EQUAL -> {
                criteriaChain.add(Criteria.where(field).`is`(value))
            }

            QueryOperation.NOT_EQUAL -> {
                criteriaChain.add(
                    Criteria.where(field).ne(value)
                )
            }

            QueryOperation.GREATER_THAN -> {
                value?.let {
                    criteriaChain.add(
                        Criteria.where(field).gt(it)
                    )
                }
            }

            QueryOperation.GREATER_THAN_EQUAL -> {
                value?.let {
                    criteriaChain.add(
                        Criteria.where(field).gte(it)
                    )
                }
            }

            QueryOperation.LESS_THAN -> {
                value?.let {
                    criteriaChain.add(
                        Criteria.where(field).lt(it)
                    )
                }
            }

            QueryOperation.LESS_THAN_EQUAL -> {
                value?.let {
                    criteriaChain.add(
                        Criteria.where(field).lte(it)
                    )
                }
            }

            else -> {
                specificType(field, value, operation, required)
            }
        }

        return this
    }

    /**
     * ## addCriteria()
     * Function for add criteria specified with/out this helper
     *
     * [criteria] A criteria you must provided
     */
    fun addCriteria(criteria: Criteria) {
        criteriaChain.add(criteria)
    }

    private fun specificType(field: String, value: Any?, operation: QueryOperation, required: Boolean): QueryHelper {
        if (value?.isString() == true && required && value.toString().isEmpty()) {
            println("QueryHelper:: Specified value is string and the required flag is true not allowed to be empty")
            return this
        } else if (value?.isList() == true && required && (value as Collection<*>).isEmpty()) {
            println("QueryHelper:: Specified value is collection and the required flag is true not allowed to be empty")
            return this
        }

        when (operation) {
            QueryOperation.MATCH_ANY -> {
                criteriaChain.add(Criteria.where(field).regex(".*$value.*", "i"))
            }
            QueryOperation.MATCH_END -> {
                criteriaChain.add(Criteria.where(field).regex("$value$", "i"))
            }
            QueryOperation.MATCH_START -> {
                criteriaChain.add(Criteria.where(field).regex("^$value", "i"))
            }

            QueryOperation.EQUAL_IN -> {
                criteriaChain.add(Criteria.where(field).`in`(value))
            }

            QueryOperation.NOT_IN -> {
                criteriaChain.add(Criteria.where(field).nin(value))
            }

            else -> {
                return this
            }
        }
        return this
    }

    /**
     * ## and()
     * Function for make addOne() before this method chain to the andOperator
     */
    fun and(): QueryHelper {
        criteria = criteria.andOperator(*criteriaChain.map { it }.toTypedArray())
        criteriaChain = emptyList<Criteria>().toMutableList()
        return this
    }

    /**
     * ## or
     * Function for make addOne() before this method chain to the orOperator
     */
    fun or(): QueryHelper {
        criteria = criteria.orOperator(*criteriaChain.map { it }.toTypedArray())
        criteriaChain = emptyList<Criteria>().toMutableList()
        return this
    }

    /**
     * ## build
     * Function for returning Criteria for mongoTemplate
     * ## Note:
     * - Before build() , you must identifying addOne() and then specify what you want eq: and() , or()
     * - Using build() only you must specified Query.addCriteria or something to attach criteria to the query
     */
    fun build(): Criteria {
        return criteria
    }

    /**
     * ## BuildQuery
     * Function for returning Query() for mongoTemplate
     * ## Note:
     * - Before buildQuery() , you must identifying addOne() and then specify what you want eq: and() , or()
     */
    fun buildQuery(): Query {
        return Query().addCriteria(criteria)
    }


}