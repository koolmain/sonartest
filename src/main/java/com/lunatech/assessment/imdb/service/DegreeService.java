package com.lunatech.assessment.imdb.service;

import com.lunatech.assessment.imdb.dto.DegreeDto;
public interface DegreeService {

    DegreeDto getDegreeOfReachbetweenActors(String targetActor, String sourceActor); 
    
}

