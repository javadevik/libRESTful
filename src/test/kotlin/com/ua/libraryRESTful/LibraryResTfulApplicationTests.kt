package com.ua.libraryRESTful

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.testcontainers.containers.PostgreSQLContainer

@SpringBootTest
class LibraryResTfulApplicationTests(
	@Autowired
	private val dataSourceContainer: PostgreSQLContainer<*>
) {

	@Test
	fun contextLoads() {
	}

	@Test
	@DisplayName("test container is up and running")
	fun containerTest() {
		Assertions.assertTrue(dataSourceContainer.isRunning)
	}

}
