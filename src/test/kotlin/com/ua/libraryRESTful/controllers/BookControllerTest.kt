package com.ua.libraryRESTful.controllers

import com.ua.libraryRESTful.factory.MapFactory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.Sql.ExecutionPhase
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Sql("/init_import_books.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql("/delete_import_books.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
class BookControllerTest(
    @Autowired
    private val webTestClient: WebTestClient,
    @Autowired
    @Qualifier("mapBookFactory")
    private val mapFactory: MapFactory
) {
    @Test
    @DisplayName("test of operation of create book")
    fun saveTest() {
        val response = webTestClient
            .post()
            .uri("/books")
            .body(BodyInserters.fromValue(mapFactory.create()))
            .exchange()

        response.expectStatus().isOk
    }

    @Test
    @DisplayName("test of finding book by id with http code 200")
    fun findByIdTestSuccess() {
        val response = webTestClient
            .get()
            .uri { uriBuilder ->
                uriBuilder.path("/books/info")
                    .queryParam("bookId", 1)
                    .build()
            }
            .exchange()
        response.expectStatus().isOk
    }

    @Test
    @DisplayName("test of finding book by id with http code 404")
    fun findByIdTestLoose() {
        val response = webTestClient
            .get()
            .uri { uriBuilder ->
                uriBuilder.path("/books/info")
                    .queryParam("bookId", -1)
                    .build()
            }
            .exchange()
        response.expectStatus().is4xxClientError
    }

    @Test
    @DisplayName("test of finding all")
    fun findAllTest() {
        val response = webTestClient
            .get()
            .uri("/books")
            .exchange()
        response.expectStatus().isOk
        val isEmpty = response.expectBody().returnResult().responseBody?.isEmpty() ?: true
        Assertions.assertFalse(isEmpty)
    }

    @Test
    @DisplayName("test of search by any params")
    fun searchTest() {
        val response = webTestClient
            .get()
            .uri { uriBuilder ->
                uriBuilder.path("/books/search")
                    .queryParam("id", 1)
                    .build()
            }
            .exchange()

        response.expectStatus().isOk
    }

    @Test
    @DisplayName("test of updating book with status 200")
    fun updateTestSuccess() {
        val response = webTestClient
            .patch()
            .uri { uriBuilder ->
                uriBuilder.path("/books")
                    .queryParam("bookId", 1)
                    .build()
            }
            .body(BodyInserters.fromValue(mapFactory.createUpdated()))
            .exchange()

        response.expectStatus().isOk
    }

    @Test
    @DisplayName("test of updating book with status 403")
    fun updateTestLoose() {
        val response = webTestClient
            .patch()
            .uri { uriBuilder ->
                uriBuilder.path("/books")
                    .queryParam("bookId", -1)
                    .build()
            }
            .body(BodyInserters.fromValue(mapFactory.createUpdated()))
            .exchange()

        response.expectStatus().is4xxClientError
    }

    @Test
    @DisplayName("test of setting exclude with status 200")
    fun setExcludeTestSuccess() {
        val response = webTestClient
            .put()
            .uri { uriBuilder ->
                uriBuilder.path("/books")
                    .queryParam("bookId", 1)
                    .queryParam("reasonExclude", "Need repair")
                    .build()
            }
            .exchange()
        response.expectStatus().isOk
    }

    @Test
    @DisplayName("test of setting exclude with status 403")
    fun setExcludeTestLoose() {
        val response = webTestClient
            .put()
            .uri { uriBuilder ->
                uriBuilder.path("/books")
                    .queryParam("bookId", 1)
                    .build()
            }
            .exchange()
        response.expectStatus().is4xxClientError
    }

    @Test
    @DisplayName("test delete book with status 200")
    fun deleteTestSuccess() {
        val response = webTestClient
            .delete()
            .uri { uriBuilder ->
                uriBuilder.path("/books")
                    .queryParam("bookId", 1)
                    .build()
            }
            .exchange()
        response.expectStatus().isOk
    }

    @Test
    @DisplayName("test delete book with status 403")
    fun deleteTestLoose() {
        val response = webTestClient
            .delete()
            .uri { uriBuilder ->
                uriBuilder.path("/books")
                    .queryParam("bookId", -1)
                    .build()
            }
            .exchange()
        response.expectStatus().is4xxClientError
    }

}