package com.ua.libraryRESTful.services.impl

import com.ua.libraryRESTful.entities.LibraryLogEntity
import com.ua.libraryRESTful.repositories.LibraryLogRepository
import com.ua.libraryRESTful.services.BookStrippedService
import com.ua.libraryRESTful.services.CustomerStrippedService
import com.ua.libraryRESTful.services.LibraryLogService
import com.ua.libraryRESTful.util.DateService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import kotlin.math.log

@Service
class LibraryLogServiceImpl(
    private val libraryLogRepository: LibraryLogRepository,
    private val customerService: CustomerStrippedService,
    private val bookService: BookStrippedService,
    private val dateService: DateService
) : LibraryLogService {

    override fun findById(id: Long): LibraryLogEntity? {
        return libraryLogRepository.findByIdOrNull(id)
    }

    override fun findAll(): List<LibraryLogEntity> {
        return libraryLogRepository.findAll()
    }

    override fun save(
        dateTaking: String,
        dateShouldReturn: String,
        bookId: Long,
        customerId: Long
    ): LibraryLogEntity? {
        val book = bookService.findById(bookId) ?: return null

        if (book.isBusy || book.isExcluded)
            return null

        val customer = customerService.findById(customerId) ?: return null

        val dateTakingTimestamp = dateService.parseToTimestamp(dateTaking)
        val dateShouldReturnTimestamp = dateService.parseToTimestamp(dateShouldReturn)

        book.isBusy = true

        val libraryLog = LibraryLogEntity(
            null, dateTakingTimestamp,
            dateShouldReturnTimestamp, null,
            book, customer
        )

        return libraryLogRepository.save(libraryLog)
    }

    override fun update(
        logId: Long,
        dateTkng: String,
        dateShouldRtrn: String,
        bookId: Long,
        customerId: Long
    ): LibraryLogEntity? {
        val logToUpdate = findById(logId) ?: return null

        val bookFound = bookService.findById(bookId) ?: return null

        if (bookFound.isBusy || bookFound.isExcluded)
            return null

        logToUpdate.book.isBusy = false //need to test

        bookFound.isBusy = true

        val customerFound = customerService.findById(customerId) ?: return null

        val dateTakingTimestamp = dateService.parseToTimestamp(dateTkng)
        val dateShouldReturnTimestamp = dateService.parseToTimestamp(dateShouldRtrn)

        with(logToUpdate) {
            dateTaking = dateTakingTimestamp
            dateShouldReturn = dateShouldReturnTimestamp
            book = bookFound
            customer = customerFound
        }
        return libraryLogRepository.save(logToUpdate)
    }

    override fun putDateReturning(logId: Long, date: String): LibraryLogEntity? {
        val logToUpdate = findById(logId) ?: return null
        val dateReturning = dateService.parseToTimestamp(date)
        logToUpdate.dateReturning = dateReturning
        logToUpdate.book.isBusy = false
        return libraryLogRepository.save(logToUpdate)

    }

    override fun delete(logId: Long): Long? {
        val logToDelete = findById(logId) ?: return null

        if (logToDelete.dateReturning == null)
            return null

        libraryLogRepository.deleteById(logId)
        return logId
    }

}