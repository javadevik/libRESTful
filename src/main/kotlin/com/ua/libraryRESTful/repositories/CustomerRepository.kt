package com.ua.libraryRESTful.repositories

import com.ua.libraryRESTful.entities.CustomerEntity
import org.springframework.data.repository.CrudRepository
import java.sql.Timestamp

interface CustomerRepository : CrudRepository<CustomerEntity, Long> {
    override fun findAll(): List<CustomerEntity>
    fun findAllByFirstNameAndLastName(firstName: String, lastName: String): List<CustomerEntity>
    fun findAllByFirstName(firstName: String): List<CustomerEntity>
    fun findAllByLastName(lastName: String): List<CustomerEntity>
    fun findAllByBirthDate(birthDate: Timestamp): List<CustomerEntity>
    fun findAllByAddress(address: String): List<CustomerEntity>
    fun findAllByWorkPlace(workPlace: String): List<CustomerEntity>
    fun findByPassport(passport: String): CustomerEntity

}