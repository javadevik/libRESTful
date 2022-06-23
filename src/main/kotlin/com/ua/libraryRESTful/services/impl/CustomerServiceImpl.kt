package com.ua.libraryRESTful.services.impl

import com.ua.libraryRESTful.entities.CustomerEntity
import com.ua.libraryRESTful.repositories.CustomerRepository
import com.ua.libraryRESTful.services.CustomerService
import com.ua.libraryRESTful.services.CustomerStrippedService
import com.ua.libraryRESTful.util.DateService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.Collections

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

    override fun search(
        firstName: String?,
        lastName: String?,
        birthDate: String?,
        address: String?,
        workPlace: String?,
        passport: String?
    ): List<CustomerEntity>? {
        if (passport != null) {
            return Collections.singletonList(customerRepository.findByPassport(passport))
        } else if (firstName != null && lastName != null) {
            return customerRepository.findAllByFirstNameAndLastName(firstName, lastName)
        } else if (firstName != null) {
            return customerRepository.findAllByFirstName(firstName)
        } else if (lastName != null) {
            return customerRepository.findAllByLastName(lastName)
        } else if (birthDate != null) {
            val timestamp = dateService.parseToTimestamp(birthDate)
            return customerRepository.findAllByBirthDate(timestamp)
        } else if (address != null) {
            return customerRepository.findAllByAddress(address)
        } else if (workPlace != null) {
            return customerRepository.findAllByWorkPlace(workPlace)
        }
        return null
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