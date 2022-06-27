package com.ua.libraryRESTful.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.testcontainers.containers.PostgreSQLContainer
import javax.sql.DataSource

@Configuration
class TestDataSourceConfig {

    companion object {
        @JvmStatic
        private val dataSourceContainer = PostgreSQLContainer("postgres:13-alpine")
            .withExposedPorts(7963)
            .withDatabaseName("librarytestdb")
            .withUsername("postgres")
            .withPassword("root")
            .apply { start() }
    }

    @Bean
    fun postgreSQLContainer(): PostgreSQLContainer<*> {
        return dataSourceContainer;
    }

    @Bean
    fun testDataSource(postgreSQLContainer: PostgreSQLContainer<*>): DataSource {
        val config = HikariConfig().apply {
            driverClassName = postgreSQLContainer.driverClassName
            jdbcUrl = postgreSQLContainer.jdbcUrl
            username = postgreSQLContainer.username
            password = postgreSQLContainer.password
        }
        return HikariDataSource(config)
    }
}