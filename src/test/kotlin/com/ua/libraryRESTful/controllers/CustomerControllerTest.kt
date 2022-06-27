package com.ua.libraryRESTful.controllers

import com.ua.libraryRESTful.factory.MapFactory
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation::class)
class CustomerControllerTest(
    @Autowired
    private val dataSourceContainer: PostgreSQLContainer<*>,
    @Autowired
    private val webTestClient: WebTestClient,
    @Autowired
    @Qualifier("mapCustomerFactory")
    private val mapFactory: MapFactory
) {
    @Test
    @Order(1)
    @DisplayName("test container is up and running")
    fun dataSourceTest() {
        Assertions.assertTrue(dataSourceContainer.isRunning)
    }

    @Test
    @Order(2)
    @DisplayName("test customer creation")
    fun saveTest() {
        val response = webTestClient
            .post()
            .uri {uriBuilder -> uriBuilder.path("/customers")
                .queryParam("birthDate", "1994-06-18")
                .build()
            }
            .body(BodyInserters.fromValue(mapFactory.create()))
            .exchange()
        response.expectStatus().isCreated
    }

    @Test
    @Order(2)
    @DisplayName("test find customer by id with status 200")
    fun findByIdTestSuccess() {
        val response = webTestClient
            .get()
            .uri {uriBuilder -> uriBuilder.path("/customers/info")
                .queryParam("customerId", 1)
                .build()
            }
            .exchange()
        response.expectStatus().isOk
    }

    @Test
    @Order(3)
    @DisplayName("test find customer by id with status 404")
    fun findByIdTestLoose() {
        val response = webTestClient
            .get()
            .uri {uriBuilder -> uriBuilder.path("/customers/info")
                .queryParam("customerId", -1)
                .build()
            }
            .exchange()
        response.expectStatus().is4xxClientError
    }

    @Test
    @Order(4)
    @DisplayName("test find all customer")
    fun findAllTest() {
        val response = webTestClient
            .get()
            .uri("/customers")
            .exchange()
        response.expectStatus().isOk
    }

    @Test
    @Order(5)
    @DisplayName("test of search customer by any params")
    fun searchTest() {
        val response = webTestClient
            .get()
            .uri {uriBuilder -> uriBuilder.path("/customers/search")
                .queryParam("firstName", "Bob")
                .build()
            }
            .exchange()
        response.expectStatus().isOk
    }

    @Test
    @Order(6)
    @DisplayName("test customer update with status 200")
    fun updateTestSuccess() {
        val response = webTestClient
            .patch()
            .uri{uriBuilder -> uriBuilder.path("/customers")
                .queryParam("customerId", 1)
                .queryParam("birthDate", "1995-06-18")
                .build()
            }
            .body(BodyInserters.fromValue(mapFactory.createUpdated()))
            .exchange()
        response.expectStatus().isOk
    }

    @Test
    @Order(7)
    @DisplayName("test customer update with status 403")
    fun updateTestLoose() {
        val response = webTestClient
            .patch()
            .uri{uriBuilder -> uriBuilder.path("/customers")
                .queryParam("customerId", -1)
                .queryParam("birthDate", "1995-06-18")
                .build()
            }
            .body(BodyInserters.fromValue(mapFactory.createUpdated()))
            .exchange()
        response.expectStatus().is4xxClientError
    }

    @Test
    @Order(8)
    @DisplayName("test customer delete with status 403")
    fun deleteTestLoose() {
        val response = webTestClient
            .delete()
            .uri {uriBuilder -> uriBuilder.path("/customers")
                .queryParam("customerId", -1)
                .build()
            }
            .exchange()
        response.expectStatus().is4xxClientError
    }

    @Test
    @Order(9)
    @DisplayName("test customer delete with status 200")
    fun deleteTestSuccess() {
        val response = webTestClient
            .delete()
            .uri {uriBuilder -> uriBuilder.path("/customers")
                .queryParam("customerId", 1)
                .build()
            }
            .exchange()
        response.expectStatus().isOk
    }
}