package com.lunatech.assessment.imdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lunatech.assessment.imdb.service.DegreeService;

@RestController
@RequestMapping("/degree")
public class DegreeController {
    
    @Autowired
    private DegreeService ; 

    @GetMapping(value = "/{targetActor}/{sourceActor}")
    public int getDegreeForAntor(@PathVariable String targetActor, @PathVariable String sourceActor) throws Exception{
        int degree = degreeService.getDegreeOfReachbetweenActors(targetActor, sourceActor);
        return degree; 
    }
}

