package com.lunatech.assessment.imdb.service;

import java.util.Optional;

import com.lunatech.assessment.imdb.model.Name;
import com.lunatech.assessment.imdb.model.summary.NameSmmary;

public interface NameService {

    Optional<Name> getNameById(String id); 
    Optional<NameSmmary> getNameSummaryById(String id);
    
}

