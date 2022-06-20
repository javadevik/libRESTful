package com.ua.libraryRESTful.services

import com.ua.libraryRESTful.entities.CustomerEntity

interface CustomerStrippedService {
    fun findById(customerId: Long): CustomerEntity?
}