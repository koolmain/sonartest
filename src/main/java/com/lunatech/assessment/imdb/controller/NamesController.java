package com.lunatech.assessment.imdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lunatech.assessment.imdb.constants.ImdbConstants;
import com.lunatech.assessment.imdb.exceptions.ImdbNotFoundException;
import com.lunatech.assessment.imdb.model.Name;
import com.lunatech.assessment.imdb.model.summary.NameSmmary;
import com.lunatech.assessment.imdb.service.NameService;
import com.lunatech.assessment.imdb.util.ImdbUtils;

@RestController
@RequestMapping("/name")
public class NamesController {
    
    @Autowired
    private NameService nameService; 

    @Autowired
    ImdbUtils utils; 

    

    @GetMapping(value = "summary/{id}", produces = "application/hal+json")
    public NameSmmary getNameSummary(@PathVariable String id){
        return nameService.getNameSummaryById(id)
                    .orElseThrow(()-> new ImdbNotFoundException(utils.getLocalMessage(ImdbConstants.NAME_NOT_FOUND, id)));    

    }

    @GetMapping(value = "details/{id}", produces = "application/hal+json")
    public Name getNameDetails(@PathVariable String id){
        return nameService.getNameById(id)
                    .orElseThrow(()-> new ImdbNotFoundException(utils.getLocalMessage(ImdbConstants.NAME_NOT_FOUND, id)));    
    }
}

