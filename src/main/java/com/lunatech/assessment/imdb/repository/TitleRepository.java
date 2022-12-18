package com.lunatech.assessment.imdb.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.lunatech.assessment.imdb.model.Title;

public interface TitleRepository  extends CrudRepository<Title, String > {

    List<Title> findByOriginalTitleOrPrimaryTitleContainingIgnoreCase(String originalTitle,String primaryTitle,Pageable pageable);

    @Query(value="SELECT title from Title title join title.rating where title.genres like %:genre%  order by title.rating.averageRating desc")
    Page<Title> findTopRatedTitleByGenres(String genre, Pageable pageable);

    @Query(value = "select title from Title title where title.tconst in :idList")
    List<Title> getAllTitlesFromIds(List<String> idList); 
    
}