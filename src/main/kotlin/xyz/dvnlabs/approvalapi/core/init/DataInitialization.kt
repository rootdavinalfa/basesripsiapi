/*
 * Copyright (c) 2021.
 * Davin Alfarizky Putra Basudewa
 * Skripsi BaseAPI
 * Educational Purposes & Reference Only
 */

package xyz.dvnlabs.approvalapi.core.init

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import xyz.dvnlabs.approvalapi.dto.UserRegister
import xyz.dvnlabs.approvalapi.entity.Drugs
import xyz.dvnlabs.approvalapi.entity.Role
import xyz.dvnlabs.approvalapi.service.DrugsService
import xyz.dvnlabs.approvalapi.service.RoleService
import xyz.dvnlabs.approvalapi.service.UserService

@Component
class DataInitialization : ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private lateinit var roleService: RoleService

    @Autowired
    private lateinit var userService: UserService

    @Autowired
    private lateinit var drugsService: DrugsService


    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        createUser()
        createDrug()
    }

    @Transactional
    fun createUser() {
        if (userService.isInitialized()) return

        // Create Role Gudang
        val roleGudang = roleService.save(
            Role(
                roleName = "ROLE_GUDANG"
            )
        )

        // Create Role Gudang
        val roleValidasiGudang = roleService.save(
            Role(
                roleName = "ROLE_VGUDANG"
            )
        )

        // Create Role ADMIN
        val roleAdmin = roleService.save(
            Role(
                roleName = "ROLE_ADMIN"
            )
        )

        // Create Role Apotik
        val roleApotik = roleService.save(
            Role(
                roleName = "ROLE_APOTIK"
            )
        )

        // Create Role Deliver
        val roleDeliver = roleService.save(
            Role(
                roleName = "ROLE_DELIVER"
            )
        )

        // Create user admin
        val admin = userService.register(
            UserRegister(
                email = "dbasudewa@gmail.com",
                userName = "rootdavinalfa",
                password = "123456"
            )
        )
        userService.attachRole(roleAdmin.id, admin.id)

        // Create user gudang
        val gudang = userService.register(
            UserRegister(
                email = "gudang@gudang.com",
                userName = "gudang",
                password = "123456"
            )
        )
        userService.attachRole(roleGudang.id, gudang.id)

        // Create user validasi gudang
        val validasiGudang = userService.register(
            UserRegister(
                email = "vgudang@gudang.com",
                userName = "vgudang",
                password = "123456"
            )
        )
        userService.attachRole(roleValidasiGudang.id, validasiGudang.id)

        // Create user apotik
        val apotik = userService.register(
            UserRegister(
                email = "apotik@apotik.com",
                userName = "apotik",
                password = "123456"
            )
        )
        userService.attachRole(roleApotik.id, apotik.id)

        // Create user apotik
        val deliver = userService.register(
            UserRegister(
                email = "deliver@deliver.com",
                userName = "deliver",
                password = "123456"
            )
        )
        userService.attachRole(roleDeliver.id, deliver.id)

    }

    fun createDrug() {
        if (drugsService.isInitialized()) return

        val listDrugs = listOf(
            Drugs(
                drugName = "Betadine",
                classified = "LUAR",
                qty = 10000.0
            ),
            Drugs(
                drugName = "Hansplast",
                classified = "LUAR",
                qty = 10000.0
            ),
            Drugs(
                drugName = "BISOLVON FLU BATUK 60 ML",
                classified = "DALAM",
                qty = 10000.0
            ),
            Drugs(
                drugName = "CALADINE LOTION 60 ML",
                classified = "LUAR",
                qty = 10000.0
            ), Drugs(
                drugName = "INSULIN REGULAR 40 IU INJ",
                classified = "DALAM",
                qty = 10000.0
            ), Drugs(
                drugName = "KOMIX HERBAL LEMON",
                classified = "LUAR",
                qty = 10000.0
            ), Drugs(
                drugName = "MADU TJ SUPER 150 ML",
                classified = "LUAR",
                qty = 10000.0
            ), Drugs(
                drugName = "MINYAK TELON CAP LANG 60 ML",
                classified = "LUAR",
                qty = 10000.0
            ), Drugs(
                drugName = "NEUROBION 5000 TAB",
                classified = "LUAR",
                qty = 10000.0
            )
        )

        listDrugs.forEach {
            drugsService.save(
                it
            )
        }
    }

}