/*
 * Copyright (c) 2021. 
 * Davin Alfarizky Putra Basudewa <dbasudewa@gmail.com>
 * Skripshit API 
 */

package xyz.dvnlabs.approvalapi.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*
import xyz.dvnlabs.approvalapi.dto.UnitDTO
import xyz.dvnlabs.approvalapi.mapper.UnitMapper
import xyz.dvnlabs.approvalapi.service.UnitService

@RequestMapping("/unit")
@RestController
@Api(tags = ["Unit Controller"])
class UnitController {

    @Autowired
    private lateinit var unitService: UnitService

    @Autowired
    private lateinit var unitMapper: UnitMapper

    @GetMapping("/{id}")
    @ApiOperation("Find by id")
    fun findById(@PathVariable id: String): UnitDTO? {
        return unitMapper.asDTO(unitService.findById(id))
    }

    @PostMapping
    @ApiOperation("Save")
    fun save(
        @RequestBody units: UnitDTO
    ): UnitDTO {
        val emp = unitMapper.asEntity(units)
        return unitMapper.asDTO(unitService.save(emp))
    }


    @PutMapping("/{id}")
    @ApiOperation("Update")
    fun update(
        @PathVariable id: String,
        @RequestBody units: UnitDTO
    ): UnitDTO {
        units.unitID = id
        val emp = unitMapper.asEntity(units)
        return unitMapper.asDTO(unitService.update(emp))
    }


    @GetMapping("/list")
    @ApiOperation("List")
    fun list(): List<UnitDTO> {
        return unitMapper.asDTOList(unitService.findAll())
    }


    @GetMapping("/page")
    @ApiOperation("Page")
    fun listPage(pageable: Pageable): Page<UnitDTO> {
        return unitService.findAllPage(pageable).map {
            return@map unitMapper.asDTO(it)
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete")
    fun delete(@PathVariable id: String) {
        unitService.delete(id)
    }

}