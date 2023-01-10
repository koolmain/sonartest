package com.lunatech.assessment.imdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lunatech.assessment.imdb.dto.DegreeDto;
import com.lunatech.assessment.imdb.service.DegreeService;

@RestController
@RequestMapping("/degree")
public class DegreeController {
    
    @Autowired
    @Qualifier("iterative")
    private DegreeService degreeService; 

    @GetMapping(value = "/{targetActor}/{sourceActor}")
    public DegreeDto getDegreeForActor(@PathVariable final String targetActor, @PathVariable final String sourceActor){
        return degreeService.getDegreeOfReachbetweenActors(targetActor, sourceActor);
    }
}

