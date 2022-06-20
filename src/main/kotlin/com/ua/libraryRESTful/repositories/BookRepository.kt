package com.ua.libraryRESTful.repositories

import com.ua.libraryRESTful.entities.BookEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface BookRepository : CrudRepository<BookEntity, Long> {
    override fun findAll(): List<BookEntity>
    fun findByTitle(title: String): BookEntity
    fun findAllByYear(year: Int): List<BookEntity>
    fun findAllByGenre(genre: String): List<BookEntity>
    fun findAllByPublisher(publisher: String): List<BookEntity>

    @Query(value = "SELECT * FROM books WHERE is_busy = false AND is_excluded = false", nativeQuery = true)
    fun findAllFree(): List<BookEntity>

    fun findAllByNumberOfPage(number: Int): List<BookEntity>
}