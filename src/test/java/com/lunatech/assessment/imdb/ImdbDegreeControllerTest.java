package com.lunatech.assessment.imdb;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
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
@AutoConfigureRestDocs(outputDir = "docs/snippets")
@ActiveProfiles("test")
@Sql(scripts = {"classpath:schema-h2.sql", } )
@Sql(scripts = {"/testdata/names.sql", "/testdata/titles.sql","/testdata/crew.sql","/testdata/ratings.sql", "/testdata/principals.sql"} )
@Transactional
class ImdbDegreeControllerTest {

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
    @WithMockUser(username = "user",roles = {"DEGREE"})
	void DegreeFetchForVinDiselToKevnBacon() throws Exception {
		this.mockMvc.perform(get("/degree/nm0000102/nm0004874")).andDo(print()).andExpect(status().isOk())
        .andExpect(jsonPath("$.baconDegree", equalTo(2)))
        .andExpect(jsonPath("$.path.length()", equalTo(3)))
        .andExpect(jsonPath("$.path.[0].name.primaryName", equalTo("Vin Diesel")))
        .andExpect(jsonPath("$.path.[0].title.originalTitle", equalTo("The Iron Giant")))
        .andExpect(jsonPath("$.path.[1].name.primaryName", equalTo("Jennifer Aniston")))
        .andExpect(jsonPath("$.path.[1].title.originalTitle", equalTo("Picture Perfect")))
        .andExpect(jsonPath("$.path.[2].name.primaryName", equalTo("Kevin Bacon")))
        .andDo(document("degreeVinDisel",preprocessRequest(modifyHeaders().remove("Host")), preprocessResponse(prettyPrint())));
	}

    @Test
    @WithMockUser(username = "user",roles = {"DEGREE"})
	void DegreeFetchForJayMohrToKevnBacon() throws Exception {
		this.mockMvc.perform(get("/degree/nm0000102/nm0001542")).andDo(print()).andExpect(status().isOk())
        .andExpect(jsonPath("$.baconDegree", equalTo(1)))
        .andExpect(jsonPath("$.path.length()", equalTo(2)))
        .andExpect(jsonPath("$.path.[0].name.primaryName", equalTo("Jay Mohr")))
        .andExpect(jsonPath("$.path.[0].title.originalTitle", equalTo("Picture Perfect")))
        .andExpect(jsonPath("$.path.[1].name.primaryName", equalTo("Kevin Bacon")))
        .andDo(document("degreeJayMohr",preprocessRequest(modifyHeaders().remove("Host")), preprocessResponse(prettyPrint())));
	}

    @Test
    @WithMockUser(username = "user",roles = {"DEGREE"})
	void DegreeFetchForJayMohrToNonExistingTarget() throws Exception {
		this.mockMvc.perform(get("/degree/nm00002/nm0001542")).andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status", equalTo("NOT_FOUND")))
        .andExpect(jsonPath("$.message", equalTo("Name with id nm00002 not found"))); 
	}   
    
    @Test
    @WithMockUser(username = "user",roles = {"DEGREE"})
	void DegreeFetchForNotExistingSourceToKevnBacon() throws Exception {
		this.mockMvc.perform(get("/degree/nm0000102/nm00015")).andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.status", equalTo("NOT_FOUND")))
        .andExpect(jsonPath("$.message", equalTo("Name with id nm00015 not found"))); 
	}        

    @Test
    @WithMockUser(username = "user",roles = {})
	void DegreeFetchForJayMohrToKevnBaconWithNoRole() throws Exception {
		this.mockMvc.perform(get("/degree/nm0000102/nm0001542")).andDo(print())
        .andExpect(status().is4xxClientError())
        .andDo(document("degreeWithoutRole",preprocessRequest(modifyHeaders().remove("Host")), preprocessResponse(prettyPrint())));


	}



}
