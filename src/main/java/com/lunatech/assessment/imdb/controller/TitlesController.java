package com.lunatech.assessment.imdb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lunatech.assessment.imdb.constants.ImdbI18NConstants;
import com.lunatech.assessment.imdb.dto.TitleDTO;
import com.lunatech.assessment.imdb.service.TitleService;
import com.lunatech.assessment.imdb.util.ImdbUtils;
import com.lunatech.assessment.imdb.exceptions.ImdbNotFoundException;

@RestController
@RequestMapping("/title")
public class TitlesController {

@Autowired
private TitleService titleService;
@Autowired
private ImdbUtils utils;

@GetMapping(value="/details/{id}" ,produces = "application/hal+json" )
public TitleDTO geTitleDetails(@PathVariable String id){
    return titleService.getTitleById(id)
        .orElseThrow(()-> new ImdbNotFoundException(utils.getLocalMessage(ImdbI18NConstants.TITLE_NOT_FOUND, id)));    
}

@GetMapping(value="/toprated/genre/{genre:[a-zA-Z]+}", produces = "application/hal+json")
public List<TitleDTO> fetchTopRatedTitlesBygenre(@PathVariable String genre, @RequestParam(defaultValue = "0") int page){
    return  titleService.fetchTitleByGenre(genre, page); 
}

@GetMapping(value="/name/{titleName:.*}", produces = "application/hal+json")
public List<TitleDTO> fetchTitleByPrimaryOrOriginalTitle(@PathVariable String titleName, @RequestParam(defaultValue = "0") int page){
    return titleService.fetchTitlesByTitleName(titleName, page); 
}

}


