package com.ua.libraryRESTful.services.impl

import com.ua.libraryRESTful.entities.CustomerEntity
import com.ua.libraryRESTful.repositories.CustomerRepository
import com.ua.libraryRESTful.services.CustomerService
import com.ua.libraryRESTful.services.CustomerStrippedService
import com.ua.libraryRESTful.util.DateService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository,
    private val dateService: DateService
) : CustomerService, CustomerStrippedService {

    override fun findById(customerId: Long): CustomerEntity? {
        return customerRepository.findByIdOrNull(customerId)
    }

    override fun findAll(): List<CustomerEntity> {
        return customerRepository.findAll()
    }

    override fun findAllByFirstAndLastNames(
        fisrtName: String,
        lastName: String
    ): List<CustomerEntity> {
        return customerRepository.findAllByFirstNameAndLastName(fisrtName, lastName)
    }

    override fun findAllByFirstName(fisrtName: String): List<CustomerEntity> {
        return customerRepository.findAllByFirstName(fisrtName)
    }

    override fun findAllByLastName(lastName: String): List<CustomerEntity> {
        return customerRepository.findAllByLastName(lastName)
    }

    override fun findAllByBirthDate(birthDate: String): List<CustomerEntity> {
        val timestamp = dateService.parseToTimestamp(birthDate)
        return customerRepository.findAllByBirthDate(timestamp)
    }

    override fun findAllByAddress(address: String): List<CustomerEntity> {
        return customerRepository.findAllByAddress(address)
    }

    override fun findAllByWorckPlace(workPlace: String): List<CustomerEntity> {
        return customerRepository.findAllByWorkPlace(workPlace)
    }

    override fun findByPassport(passport: String): CustomerEntity {
        return customerRepository.findByPassport(passport)
    }

    override fun save(birthDate: String, customer: CustomerEntity): CustomerEntity {
        val timestamp = dateService.parseToTimestamp(birthDate)
        customer.birthDate = timestamp
        return customerRepository.save(customer)
    }

    override fun update(
        customerId: Long,
        birth: String,
        customerUpdate: CustomerEntity
    ): CustomerEntity? {
        val customerToUpdate = findById(customerId)?: return null
        val timestamp = dateService.parseToTimestamp(birth)

        with(customerToUpdate) {
            firstName = customerUpdate.firstName
            lastName = customerUpdate.lastName
            birthDate = timestamp
            address = customerUpdate.address
            workPlace = customerUpdate.workPlace
            position = customerUpdate.position
            phone = customerUpdate.phone
            passport = customerUpdate.passport
        }
        return customerRepository.save(customerToUpdate)
    }

    override fun delete(customerId: Long): Long? {
        val customerToDelete = findById(customerId)?: return null

        val isNotReturningAnyBook = customerToDelete.logs.stream()
            .anyMatch { l-> l.dateReturning == null }

        if (isNotReturningAnyBook)
            return null

        customerRepository.deleteById(customerId)
        return customerId
    }

}