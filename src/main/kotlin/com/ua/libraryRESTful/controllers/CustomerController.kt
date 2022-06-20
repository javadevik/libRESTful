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

    @GetMapping("/firstName/lastName")
    fun findAllByFirstAndLastNames(
        @RequestParam firstName: String,
        @RequestParam lastName: String
    ): ResponseEntity<List<CustomerEntity>> {
        val customers = customerService.findAllByFirstAndLastNames(firstName, lastName)
        return ResponseEntity(customers, HttpStatus.OK)
    }

    @GetMapping("/firstName")
    fun findAllByFirstName(
        @RequestParam firstName: String
    ): ResponseEntity<List<CustomerEntity>> {
        val customers = customerService.findAllByFirstName(firstName)
        return ResponseEntity(customers, HttpStatus.OK)
    }

    @GetMapping("/lastName")
    fun findAllByLastName(
        @RequestParam lastName: String
    ): ResponseEntity<List<CustomerEntity>> {
        val customers = customerService.findAllByLastName(lastName)
        return ResponseEntity(customers, HttpStatus.OK)
    }

    @GetMapping("/birthDate")
    fun findAllByBirthDate(
        @RequestParam birthDate: String
    ): ResponseEntity<List<CustomerEntity>> {
        val customers = customerService.findAllByBirthDate(birthDate)
        return ResponseEntity(customers, HttpStatus.OK)
    }

    @GetMapping("/address")
    fun findAllByAddress(
        @RequestParam address: String
    ): ResponseEntity<List<CustomerEntity>> {
        val customers = customerService.findAllByAddress(address)
        return ResponseEntity(customers, HttpStatus.OK)
    }

    @GetMapping("/workPlace")
    fun findAllByWorkPlace(
        @RequestParam workPlace: String
    ): ResponseEntity<List<CustomerEntity>> {
        val customers = customerService.findAllByWorckPlace(workPlace)
        return ResponseEntity(customers, HttpStatus.OK)
    }

    @GetMapping("/passport")
    fun findAllByPassport(
        @RequestParam passport: String
    ): ResponseEntity<CustomerEntity> {
        val customer = customerService.findByPassport(passport)
        return ResponseEntity(customer, HttpStatus.OK)
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