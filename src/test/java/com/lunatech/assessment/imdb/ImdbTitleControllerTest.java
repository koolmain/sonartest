package com.lunatech.assessment.imdb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema-h2.sql", } )
@Sql(scripts = {"/testdata/names.sql", "/testdata/titles.sql","/testdata/crew.sql","/testdata/ratings.sql", "/testdata/principals.sql"} )
@Transactional
class ImdbTitleControllerTest {

    @Autowired
    private MockMvc mockMvc; 

    @DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry){
		registry.add("spring.datasource.url", () -> "jdbc:h2:mem:testdb");
		registry.add("spring.datasource.password", () ->"sa");
		registry.add("spring.datasource.username", ()-> "");
		registry.add("spring.datasource.driver-class-name", ()-> "org.h2.Driver");
		registry.add("spring.jpa.properties.hibernate.dialect", ()-> "org.hibernate.dialect.H2Dialect");
	}
    
    @Test
    @WithMockUser(username = "user",roles = {})
	void GenreSearchForDrama() throws Exception {
		this.mockMvc.perform(get("/title/toprated/genre/Drama")).andDo(print()).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", equalTo(1)))
        .andExpect(jsonPath("$.[0].genres", containsString("Drama")))
        .andExpect(jsonPath("$.[0].tconst", equalTo("tt0119896")))
        .andExpect(jsonPath("$.[0].primaryTitle", equalTo("Picture Perfect")));
	}

    @Test
    @WithMockUser(username = "user",roles = {})
	void GenreSearchForDramaIgnoreCase() throws Exception {
		this.mockMvc.perform(get("/title/toprated/genre/drama")).andDo(print()).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", equalTo(1)))
        .andExpect(jsonPath("$.[0].genres", containsString("Drama")))
        .andExpect(jsonPath("$.[0].tconst", equalTo("tt0119896")))
        .andExpect(jsonPath("$.[0].primaryTitle", equalTo("Picture Perfect")));
	}

    @Test
    @WithMockUser(username = "user",roles = {})
	void GenreSearchForPartialWord() throws Exception {
		this.mockMvc.perform(get("/title/toprated/genre/ama")).andDo(print()).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", equalTo(1)))
        .andExpect(jsonPath("$.[0].genres", containsString("Drama")))
        .andExpect(jsonPath("$.[0].tconst", equalTo("tt0119896")))
        .andExpect(jsonPath("$.[0].primaryTitle", equalTo("Picture Perfect")));
	}

    @Test
    @WithMockUser(username = "user",roles = {})
	void GenreSearchForAction() throws Exception {
		this.mockMvc.perform(get("/title/toprated/genre/Action")).andDo(print()).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", equalTo(1)))
        .andExpect(jsonPath("$.[0].genres", containsString("Action")))
        .andExpect(jsonPath("$.[0].tconst", equalTo("tt0129167")))
        .andExpect(jsonPath("$.[0].primaryTitle", equalTo("The Iron Giant")));
	}

    @Test
    @WithMockUser(username = "user",roles = {})
	void GenreSearchForNotExisting() throws Exception {
		this.mockMvc.perform(get("/title/toprated/genre/NOTEXISTING")).andDo(print()).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", equalTo(0)));
	}

    @Test
    @WithMockUser(username = "user",roles = {})
	void NameSearchForIron() throws Exception {
		this.mockMvc.perform(get("/title/name/Iron")).andDo(print()).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", equalTo(1)))
        .andExpect(jsonPath("$.[0].titleType",  equalTo("movie")))
        .andExpect(jsonPath("$.[0].tconst", equalTo("tt0129167")))
        .andExpect(jsonPath("$.[0].primaryTitle", equalTo("The Iron Giant")));
	}

    @Test
    @WithMockUser(username = "user",roles = {})
	void NameSearchForPerfect() throws Exception {
		this.mockMvc.perform(get("/title/name/Iron")).andDo(print()).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", equalTo(1)))
        .andExpect(jsonPath("$.[0].tconst", equalTo("tt0129167")))
        .andExpect(jsonPath("$.[0].primaryTitle", equalTo("The Iron Giant")));
	}

    @Test
    @WithMockUser(username = "user",roles = {})
	void NameSearchForPartialPerfect() throws Exception {
		this.mockMvc.perform(get("/title/name/erfec")).andDo(print()).andExpect(status().isOk())
        .andExpect(jsonPath("$.length()", equalTo(1)))
        .andExpect(jsonPath("$.[0].tconst", equalTo("tt0119896")))
        .andExpect(jsonPath("$.[0].primaryTitle", equalTo("Picture Perfect")));
	}
}
