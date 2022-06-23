package com.ua.libraryRESTful.controllers

import com.ua.libraryRESTful.entities.CustomerEntity
import com.ua.libraryRESTful.services.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = ["http://localhost:3000"])
class CustomerController(
    private val customerService: CustomerService
) {

    @GetMapping("/info")
    fun findById(@RequestParam customerId: Long): ResponseEntity<CustomerEntity> {
        val customerFound = customerService.findById(customerId)
            ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity(customerFound, HttpStatus.OK)
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<CustomerEntity>> {
        val customers = customerService.findAll()
        return ResponseEntity(customers, HttpStatus.OK)
    }

    @GetMapping("/search")
    fun search(
        @RequestParam(required = false) firstName: String?,
        @RequestParam(required = false) lastName: String?,
        @RequestParam(required = false) birthDate: String?,
        @RequestParam(required = false) address: String?,
        @RequestParam(required = false) workPlace: String?,
        @RequestParam(required = false) passport: String?,
    ): ResponseEntity<List<CustomerEntity>> {
        val customers = customerService.search(firstName, lastName, birthDate,
            address, workPlace, passport)
        return ResponseEntity(customers, HttpStatus.OK)
    }

    @PostMapping
    fun save(
        @RequestParam birthDate: String,
        @RequestBody customer: CustomerEntity
    ): ResponseEntity<CustomerEntity> {
        val customerSaved = customerService.save(birthDate, customer)
        return ResponseEntity(customerSaved, HttpStatus.CREATED)
    }

    @PatchMapping
    fun update(
        @RequestParam customerId: Long,
        @RequestParam birthDate: String,
        @RequestBody customer: CustomerEntity
    ): ResponseEntity<CustomerEntity> {
        val customerUpdated = customerService.update(customerId, birthDate, customer)
            ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        return ResponseEntity(customerUpdated, HttpStatus.OK)
    }

    @DeleteMapping
    fun delete(@RequestParam customerId: Long): ResponseEntity<Long> {
        val customerIdDeleted = customerService.delete(customerId)
            ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        return ResponseEntity(customerIdDeleted, HttpStatus.OK)
    }
}