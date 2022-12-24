package com.lunatech.assessment.imdb;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import com.lunatech.assessment.imdb.model.Crew;
import com.lunatech.assessment.imdb.model.Name;
import com.lunatech.assessment.imdb.model.Principals;
import com.lunatech.assessment.imdb.model.PrincipalsId;
import com.lunatech.assessment.imdb.model.Rating;
import com.lunatech.assessment.imdb.model.Title;
import com.lunatech.assessment.imdb.repository.NamesRepository;
import com.lunatech.assessment.imdb.repository.PrincipalsRepository;

public class TestDataUtils {

    @Autowired 
	PrincipalsRepository principalsRepository; 

	@Autowired
	NamesRepository namesRepository; 
    
    @BeforeEach
	void setup(){
		Title title = createTitle("tt0295701","xXx","xXx",2002,0);
		Crew crew = createCrew(title,"nm0003418","nm0929186");
		Rating rating = createRating(title,5.9,172787);
		Name name = createName("nm0004874","Vin Diesel",1967,0,"producer,actor,director");
		Principals principal = createPrincipal(title, name,1,"actor","Xander Cage","");

		// title.setCrew(crew);
		// title.setRating(rating);
		// title.setPrincipalsList(List.of(principal));

		// repository.save(title); 
		// long s = repository.count();
		// List<Title> ls = new ArrayList()<>(); 
		// repository.findAll().iterator().forEachRemaining(ls::add);
		
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
