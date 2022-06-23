package com.ua.libraryRESTful.controllers

import com.ua.libraryRESTful.entities.BookEntity
import com.ua.libraryRESTful.services.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
@CrossOrigin(origins = ["http://localhost:3000"])
class BookController(
    private val bookService: BookService
) {
    @GetMapping("/info")
    fun findById(@RequestParam bookId: Long): ResponseEntity<BookEntity> {
        val bookEntity = bookService.findById(bookId)
            ?: return ResponseEntity(HttpStatus.NOT_FOUND)
        return ResponseEntity(bookEntity, HttpStatus.OK)
    }

    @GetMapping
    fun findAll(): ResponseEntity<List<BookEntity>> {
        val books = bookService.findAll()
        return ResponseEntity(books, HttpStatus.OK)
    }

    @GetMapping("/free")
    fun findAllFree(): ResponseEntity<List<BookEntity>> {
        val books = bookService.findAllFree()
        return ResponseEntity(books, HttpStatus.OK)
    }

    @GetMapping("/search")
    fun search(
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) author: String?,
        @RequestParam(required = false) genre: String?,
        @RequestParam(required = false) year: Int?,
        @RequestParam(required = false) publisher: String?,
        @RequestParam(required = false) numberOfPage: Int?
    ): ResponseEntity<List<BookEntity>> {
        val books = bookService.search(title, author, genre, year, publisher, numberOfPage)
        return ResponseEntity(books, HttpStatus.OK)
    }

    @PostMapping
    fun save(@RequestBody book: BookEntity): ResponseEntity<BookEntity> {
        val bookSaved = bookService.save(book)
        return ResponseEntity(bookSaved, HttpStatus.OK)
    }

    @PatchMapping
    fun update(
        @RequestParam bookId: Long,
        @RequestBody book: BookEntity
    ): ResponseEntity<BookEntity> {
        val bookUpdated = bookService.update(bookId, book)
            ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        return ResponseEntity(bookUpdated, HttpStatus.OK)
    }

    @PutMapping
    fun setAsExcluded(
        @RequestParam bookId: Long,
        @RequestParam reasonExclude: String
    ): ResponseEntity<BookEntity> {
        val bookExcluded = bookService.setExcludedStatus(bookId, reasonExclude)
            ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        return ResponseEntity(bookExcluded, HttpStatus.OK)
    }

    @DeleteMapping
    fun delete(@RequestParam bookId: Long): ResponseEntity<Long> {
        val bookIdDeleted = bookService.delete(bookId)
            ?: return ResponseEntity(HttpStatus.BAD_REQUEST)
        return ResponseEntity(bookIdDeleted, HttpStatus.OK)
    }

}