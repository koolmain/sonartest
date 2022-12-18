package com.lunatech.assessment.imdb.service;

import java.util.Optional;

import com.lunatech.assessment.imdb.model.Principals;
import com.lunatech.assessment.imdb.model.PrincipalsId;

public interface PrincipalsService {
    
    Optional<Principals> getPrincipalsById(PrincipalsId id); 

}
