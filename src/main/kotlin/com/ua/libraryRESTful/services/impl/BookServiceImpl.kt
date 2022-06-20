package com.ua.libraryRESTful.services.impl

import com.ua.libraryRESTful.entities.BookEntity
import com.ua.libraryRESTful.repositories.BookRepository
import com.ua.libraryRESTful.services.BookService
import com.ua.libraryRESTful.services.BookStrippedService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class BookServiceImpl(
    private val bookRepository: BookRepository
) : BookService, BookStrippedService {

    override fun findById(bookId: Long): BookEntity? {
        return bookRepository.findByIdOrNull(bookId)
    }

    override fun findAll(): List<BookEntity> {
        return bookRepository.findAll()
    }

    override fun findAllFree(): List<BookEntity> {
        return bookRepository.findAllFree()
    }

    override fun findByTitle(title: String): BookEntity {
        return bookRepository.findByTitle(title)
    }

    override fun findAllByYear(year: Int): List<BookEntity> {
        return bookRepository.findAllByYear(year)
    }

    override fun findAllByGenre(genre: String): List<BookEntity> {
        return bookRepository.findAllByGenre(genre)
    }

    override fun findAllByPublisher(publisher: String): List<BookEntity> {
        return bookRepository.findAllByPublisher(publisher)
    }

    override fun findAllByNumberOfPage(numberOfPage: Int): List<BookEntity> {
        return bookRepository.findAllByNumberOfPage(numberOfPage)
    }

    override fun save(book: BookEntity): BookEntity {
        return bookRepository.save(book)
    }

    override fun update(bookId: Long, bookUpdated: BookEntity): BookEntity? {
        val bookToUpdate = findById(bookId)
            ?: return null

        with(bookToUpdate) {
            title = bookUpdated.title
            author = bookUpdated.author
            genre = bookUpdated.genre
            year = bookUpdated.year
            publisher = bookUpdated.publisher
            numberOfPage = bookUpdated.numberOfPage
        }
        return bookRepository.save(bookToUpdate)
    }

    override fun setExcludedStatus(bookId: Long, reason: String): BookEntity? {
        val bookToExclude = findById(bookId)?: return null

        val isNotReturning = bookToExclude.logs.stream()
            .anyMatch { false }

        if (isNotReturning)
            return null

        with(bookToExclude) {
            isExcluded = true
            reasonExclude = reason
        }
        return bookRepository.save(bookToExclude)
    }

    override fun delete(bookId: Long): Long? {
        val bookToDelete = findById(bookId)?: return null

        val isNotReturning = bookToDelete.logs.stream()
            .anyMatch { l -> l.dateReturning == null }

        if (isNotReturning)
            return null

        bookRepository.deleteById(bookId)
        return bookId
    }
}