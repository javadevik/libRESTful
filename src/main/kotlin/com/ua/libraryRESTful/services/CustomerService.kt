package com.ua.libraryRESTful.services

import com.ua.libraryRESTful.entities.CustomerEntity
import org.springframework.util.MultiValueMap

interface CustomerService {
    fun findById(customerId: Long): CustomerEntity?
    fun findAll(): List<CustomerEntity>
    fun search(
        firstName: String?,
        lastName: String?,
        birthDate: String?,
        address: String?,
        workPlace: String?,
        passport: String?
    ): List<CustomerEntity>?
    fun save(birthDate: String, customer: CustomerEntity): CustomerEntity
    fun update(customerId: Long, birth: String, customerUpdate: CustomerEntity): CustomerEntity?
    fun delete(customerId: Long): Long?


}