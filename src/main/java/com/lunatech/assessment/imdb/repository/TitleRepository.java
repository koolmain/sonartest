package com.lunatech.assessment.imdb.repository;

import java.util.List;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.lunatech.assessment.imdb.model.Title;

public interface TitleRepository  extends CrudRepository<Title, String > {

    List<Title> findByOriginalTitleOrPrimaryTitleContainingIgnoreCase(String originalTitle,String primaryTitle,Pageable pageable);

    @Cacheable("genres")
    @Query(value="SELECT title from Title title join title.rating where lower(title.genres) like lower(concat('%', :genre,'%')) order by title.rating.averageRating desc, title.tconst desc")
    Page<Title> findTopRatedTitleByGenres(String genre, Pageable pageable);

    @Query(value = "select title from Title title where title.tconst in :idList")
    List<Title> getAllTitlesFromIds(List<String> idList); 
    
}