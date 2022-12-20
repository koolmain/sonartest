package com.lunatech.assessment.imdb.service;

import java.util.List;
import java.util.Optional;

import com.lunatech.assessment.imdb.dto.TitleDTO;
import com.lunatech.assessment.imdb.model.Title;

public interface TitleService {

    Optional<TitleDTO> getTitleById(String id); 
    Optional<Title> getTitleWithPrincipalsById(String id);
    List<Title> fetchTitlesByTitleName(String titleName, int page); 
    List<TitleDTO> fetchTitleByGenre(String genre, int page); 
    List<Title> getAllTitlesFromIds(List<String> ids );
    

    
}
