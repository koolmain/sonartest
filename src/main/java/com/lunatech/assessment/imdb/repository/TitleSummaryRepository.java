package com.lunatech.assessment.imdb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.lunatech.assessment.imdb.model.Title;
import com.lunatech.assessment.imdb.model.summary.TitleSummary;

public interface TitleSummaryRepository  extends CrudRepository<Title, String > {

    Optional<TitleSummary> findByTconst(String tconst);

    @Query(value = "select title from Title title where title.tconst in :idList")
    List<TitleSummary> getAllTitlesFromIds(List<String> idList); 
    
}