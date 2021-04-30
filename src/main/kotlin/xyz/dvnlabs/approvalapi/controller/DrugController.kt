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
import xyz.dvnlabs.approvalapi.dto.DrugsDTO
import xyz.dvnlabs.approvalapi.mapper.DrugsMapper
import xyz.dvnlabs.approvalapi.service.DrugsService

@RequestMapping("/drugs")
@RestController
@Api(tags = ["Drugs Controller"])
class DrugController {

    @Autowired
    private lateinit var drugsService: DrugsService

    @Autowired
    private lateinit var drugMapper: DrugsMapper

    @GetMapping("/{id}")
    @ApiOperation("Find by id")
    fun findById(@PathVariable id: Long): DrugsDTO? {
        return drugMapper.asDTO(drugsService.findById(id))
    }

    @PostMapping
    @ApiOperation("Save")
    fun save(
        @RequestBody units: DrugsDTO
    ): DrugsDTO {
        val emp = drugMapper.asEntity(units)
        return drugMapper.asDTO(drugsService.save(emp))
    }


    @PutMapping("/{id}")
    @ApiOperation("Update")
    fun update(
        @PathVariable id: Long,
        @RequestBody units: DrugsDTO
    ): DrugsDTO {
        units.idDrug = id
        val emp = drugMapper.asEntity(units)
        return drugMapper.asDTO(drugsService.update(emp))
    }


    @GetMapping("/list")
    @ApiOperation("List")
    fun list(): List<DrugsDTO> {
        return drugMapper.asDTOList(drugsService.findAll())
    }


    @GetMapping("/page")
    @ApiOperation("Page")
    fun listPage(pageable: Pageable): Page<DrugsDTO> {
        return drugsService.findAllPage(pageable).map {
            return@map drugMapper.asDTO(it)
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Delete")
    fun delete(@PathVariable id: Long) {
        drugsService.delete(id)
    }

}