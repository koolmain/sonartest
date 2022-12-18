package com.lunatech.assessment.imdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lunatech.assessment.imdb.model.Name;
import com.lunatech.assessment.imdb.service.NameService;

@RestController
@RequestMapping("/name")
public class NamesController {
    
    @Autowired
    private NameService nameService; 

    @GetMapping(value = "details/{id}", produces = "application/hal+json")
    public Name getName(@PathVariable String id){
        return nameService.getNameById(id).orElseThrow();
    }

}

