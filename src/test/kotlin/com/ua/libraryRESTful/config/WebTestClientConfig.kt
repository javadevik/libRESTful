package com.ua.libraryRESTful.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.web.reactive.server.WebTestClient

@Configuration
class WebTestClientConfig {
    @Bean
    fun webTestClient(): WebTestClient {
        return WebTestClient
            .bindToServer()
            .baseUrl("http://localhost:8080")
            .build()
    }
}