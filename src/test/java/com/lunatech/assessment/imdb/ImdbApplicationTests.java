package com.lunatech.assessment.imdb;

import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.contains;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import com.lunatech.assessment.imdb.model.Crew;
import com.lunatech.assessment.imdb.model.Name;
import com.lunatech.assessment.imdb.model.Principals;
import com.lunatech.assessment.imdb.model.PrincipalsId;
import com.lunatech.assessment.imdb.model.Rating;
import com.lunatech.assessment.imdb.model.Title;
import com.lunatech.assessment.imdb.repository.NamesRepository;
import com.lunatech.assessment.imdb.repository.PrincipalsRepository;
import com.lunatech.assessment.imdb.repository.TitleRepository;
import com.lunatech.assessment.imdb.service.TitleService;

@SpringBootTest
//@ActiveProfiles("test")
//@Testcontainers
// @ExtendWith(SpringExtension.class)
// @DataJpaTest
// @TestPropertySource(properties = {
//         "spring.jpa.hibernate.ddl-auto=validate"
// })
class ImdbApplicationTests {

	@Autowired
	TitleRepository repository; 

	@Autowired 
	PrincipalsRepository principalsRepository; 

	@Autowired
	NamesRepository namesRepository; 

	@Autowired
	TitleService titleService; 

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
	@Transactional
	void contextLoads() {

		System.out.println("It worked!!");

		long d = repository.count();

		Optional<Title> t1= repository.findById("tt0295701"); 
		
		Optional<Title> t = titleService.getTitleById("tt0295701");
		assertFalse(false);
	}

	@BeforeEach
	void setup(){
		long w = repository.count();
		Title title = createTitle("tt0295701","xXx","xXx",2002,0);
		Crew crew = createCrew(title,"nm0003418","nm0929186");
		Rating rating = createRating(title,5.9,172787);
		Name name = createName("nm0004874","Vin Diesel",1967,0,"producer,actor,director");
		Principals principal = createPrincipal(title, name,1,"actor","Xander Cage","");

		title.setCrew(crew);
		title.setRating(rating);
		title.setPrincipalsList(List.of(principal));

		repository.save(title); 
		long s = repository.count();
		List<Title> ls = new ArrayList<>(); 
		repository.findAll().iterator().forEachRemaining(ls::add);
		long t = principalsRepository.count(); 
		long n = namesRepository.count();
		boolean b=repository.existsById("tt78333");
		
	}

	private Title createTitle(String id, String origTitle, String primTitle, int startYear, int endYear) {
		Title title = new Title(); 
		
		title.setTconst(id);
		title.setOriginalTitle(origTitle);
		title.setPrimaryTitle(primTitle);
		title.setEndYear(endYear);
		title.setStartYear(startYear);
		return title;
	}

	private Crew createCrew(Title title, String directors, String writes) {
		Crew crew = new Crew(); 
		crew.setDirectors(directors);
		crew.setWriters(writes);
		crew.setTconst(title.getTconst());
		crew.setTitle(title);
		return crew; 
	}

	private Rating createRating(Title title, double avgRating, int numVotes) {
		Rating rating = new Rating(); 
		rating.setAverageRating(avgRating);
		rating.setNumVotes(numVotes);
		rating.setTconst(title.getTconst());
		//title.setRating(rating);
		rating.setTitle(title);
		return rating; 
	}

	private Principals createPrincipal(Title title, Name name, int ordering, String category, String characters, String job  ) {
		Principals principal = new Principals(); 

		PrincipalsId pid = new PrincipalsId(); 
		pid.setNconstId(name.getNconst());
		pid.setTconstId(title.getTconst());
		pid.setOrdering(ordering);
		principal.setId(pid);

		principal.setName1(name);
		principal.setTitle1(title);
		principal.setCategory(category);
		principal.setCharacters(characters);
		principal.setJob(job);
		return principal;
	}

	private Name createName(String id, String pname, int birthYear, int deathYear, String primaryProfession ) {
		Name name = new Name(); 
		name.setPrimaryName(pname);
		name.setBirthYear(birthYear);
		name.setDeathYear(deathYear);
		name.setNconst(id);
		name.setPrimaryProfession(primaryProfession);
		namesRepository.save(name);
		return name;
	}





}
