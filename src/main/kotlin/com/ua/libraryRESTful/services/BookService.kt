package com.ua.libraryRESTful.services

import com.ua.libraryRESTful.entities.BookEntity

interface BookService {
    fun findById(bookId: Long): BookEntity?
    fun findAll(): List<BookEntity>
    fun findByTitle(title: String): BookEntity
    fun findAllByYear(year: Int): List<BookEntity>
    fun findAllByGenre(genre: String): List<BookEntity>
    fun findAllByPublisher(publisher: String): List<BookEntity>
    fun findAllByNumberOfPage(numberOfPage: Int): List<BookEntity>
    fun save(book: BookEntity): BookEntity
    fun update(bookId: Long, bookUpdated: BookEntity): BookEntity?
    fun setExcludedStatus(bookId: Long, reason: String): BookEntity?
    fun delete(bookId: Long): Long?
    fun findAllFree(): List<BookEntity>
}