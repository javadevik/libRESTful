package com.ua.libraryRESTful.repositories

import com.ua.libraryRESTful.entities.LibraryLogEntity
import org.springframework.data.repository.CrudRepository

interface LibraryLogRepository : CrudRepository<LibraryLogEntity, Long> {
    override fun findAll(): List<LibraryLogEntity>
}