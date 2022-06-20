package com.ua.libraryRESTful.services

import com.ua.libraryRESTful.entities.LibraryLogEntity

interface LibraryLogService {
    fun findById(id: Long): LibraryLogEntity?
    fun findAll(): List<LibraryLogEntity>
    fun save(dateTaking: String, dateShouldReturn: String, bookId: Long, customerId: Long): LibraryLogEntity?
    fun update(logId: Long, dateTkng: String, dateShouldRtrn: String, bookId: Long, customerId: Long): LibraryLogEntity?
    fun putDateReturning(logId: Long, date: String): LibraryLogEntity?
    fun delete(logId: Long): Long?
}