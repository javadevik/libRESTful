package com.ua.libraryRESTful.services

import com.ua.libraryRESTful.entities.BookEntity

interface BookStrippedService {
    fun findById(bookId: Long): BookEntity?
}