package com.ua.libraryRESTful.services

import com.ua.libraryRESTful.entities.BookEntity

interface BookService {
    fun findById(bookId: Long): BookEntity?
    fun findAll(): List<BookEntity>
    fun search(
        title: String?,
        author: String?,
        genre: String?,
        year: Int?,
        publisher: String?,
        numberOfPage: Int?
    ): List<BookEntity>?
    fun save(book: BookEntity): BookEntity
    fun update(bookId: Long, bookUpdated: BookEntity): BookEntity?
    fun setExcludedStatus(bookId: Long, reason: String): BookEntity?
    fun delete(bookId: Long): Long?
    fun findAllFree(): List<BookEntity>

}