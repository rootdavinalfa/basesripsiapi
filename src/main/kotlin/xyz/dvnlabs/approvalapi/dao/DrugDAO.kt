/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.dao

import org.springframework.stereotype.Repository
import xyz.dvnlabs.approvalapi.entity.Drugs
import xyz.dvnlabs.approvalapi.entity.User

@Repository
interface DrugDAO : GenericDAO<Drugs, Long> {

    fun findFirstByOrderByIdDrugDesc(): Drugs?

}