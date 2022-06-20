package com.ua.libraryRESTful.services

import com.ua.libraryRESTful.entities.CustomerEntity

interface CustomerService {
    fun findById(customerId: Long): CustomerEntity?
    fun findAll(): List<CustomerEntity>
    fun findAllByFirstAndLastNames(fisrtName: String, lastName: String): List<CustomerEntity>
    fun findAllByFirstName(fisrtName: String): List<CustomerEntity>
    fun findAllByLastName(lastName: String): List<CustomerEntity>
    fun findAllByBirthDate(birthDate: String): List<CustomerEntity>
    fun findAllByAddress(address: String): List<CustomerEntity>
    fun findByPassport(passport: String): CustomerEntity
    fun save(birthDate: String, customer: CustomerEntity): CustomerEntity
    fun update(customerId: Long, birth: String, customerUpdate: CustomerEntity): CustomerEntity?
    fun delete(customerId: Long): Long?
    fun findAllByWorckPlace(workPlace: String): List<CustomerEntity>
}