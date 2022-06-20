package com.ua.libraryRESTful.controllers

import com.ua.libraryRESTful.entities.LibraryLogEntity
import com.ua.libraryRESTful.services.LibraryLogService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/logs")
@CrossOrigin(origins = ["http://localhost:3000"])
class LibraryLogController(
    private val logService: LibraryLogService
) {

    @GetMapping("/info")
    fun findById(@RequestParam logId: Long): ResponseEntity<LibraryLogEntity> {
        val logFound = logService.findById(logId)
            ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity(logFound, HttpStatus.OK)
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<LibraryLogEntity>> {
        val logs = logService.findAll()
        return ResponseEntity(logs, HttpStatus.OK)
    }

    @PostMapping
    fun save(
        @RequestParam dateTaking: String,
        @RequestParam dateShouldReturn: String,
        @RequestParam bookId: Long,
        @RequestParam customerId: Long
    ): ResponseEntity<LibraryLogEntity> {
        val logSaved = logService.save(dateTaking, dateShouldReturn, bookId, customerId)
            ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        return ResponseEntity(logSaved, HttpStatus.CREATED)
    }

    @PatchMapping
    fun update(
        @RequestParam logId: Long,
        @RequestParam dateTaking: String,
        @RequestParam dateShouldReturn: String,
        @RequestParam bookId: Long,
        @RequestParam customerId: Long
    ): ResponseEntity<LibraryLogEntity> {
        val logUpdated = logService.update(logId, dateTaking, dateShouldReturn, bookId, customerId)
            ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        return ResponseEntity(logUpdated, HttpStatus.OK)
    }

    @PutMapping
    fun putDateReturning(
        @RequestParam logId: Long,
        @RequestParam dateReturning: String
    ): ResponseEntity<LibraryLogEntity> {
        val logUpdated = logService.putDateReturning(logId, dateReturning)
            ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        return ResponseEntity(logUpdated, HttpStatus.OK)
    }

    @DeleteMapping
    fun delete(@RequestParam logId: Long): ResponseEntity<Long> {
        val logIdDeleted = logService.delete(logId)
            ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        return ResponseEntity(logIdDeleted, HttpStatus.OK)
    }
}