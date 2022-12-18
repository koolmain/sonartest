package com.lunatech.assessment.imdb.service;

import java.util.Optional;

import com.lunatech.assessment.imdb.model.Name;

public interface NameService {

    Optional<Name> getNameById(String id); 
    
}

