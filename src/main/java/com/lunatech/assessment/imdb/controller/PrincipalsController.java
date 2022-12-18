package com.lunatech.assessment.imdb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lunatech.assessment.imdb.model.Principals;
import com.lunatech.assessment.imdb.model.PrincipalsId;
import com.lunatech.assessment.imdb.service.PrincipalsService;

@RestController
@RequestMapping("/principals")
public class PrincipalsController {

@Autowired
private PrincipalsService principalsService;

@GetMapping(produces = "application/hal+json")
public Principals geTitle(@RequestParam String tid, @RequestParam String nid, @RequestParam int ordering){
    PrincipalsId principalsId = new PrincipalsId(tid,ordering,nid); 
    return principalsService.getPrincipalsById(principalsId).orElseThrow();    
}
    
}
