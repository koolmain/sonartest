package com.lunatech.assessment.imdb;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.contains;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@ActiveProfiles("test")
//@Testcontainers
class ImdbApplicationTests {

	// @Container
	// public static PostgreSQLContainer container = new PostgreSQLContainer()
	// 													.withUsername("postgrestest")
	// 													.withPassword("postgrestest")
	// 													.withDatabaseName("lunatechdbtest"); 
	// 												     DockerImageName.parse("postgres:13")
	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry){
		// registry.add("spring.datasource.url", container::getJdbcUrl);
		// registry.add("spring.datasource.password", container::getPassword);
		// registry.add("spring.datasource.username", container::getUsername);
		registry.add("spring.datasource.url", () -> "jdbc:h2:mem:testdb");
		registry.add("spring.datasource.password", () ->"sa");
		registry.add("spring.datasource.username", ()-> "");
		registry.add("spring.datasource.driver-class-name", ()-> "org.h2.Driver");
		registry.add("spring.jpa.properties.hibernate.dialect", ()-> "org.hibernate.dialect.H2Dialect");

		
	}
	@Test
	void contextLoads() {
		System.out.println("It worked!!");
		// assertFalse(true);
	}

	// @BeforeEach
	// void setup

}
