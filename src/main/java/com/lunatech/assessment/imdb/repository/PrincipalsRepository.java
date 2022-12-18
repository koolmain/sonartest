package com.lunatech.assessment.imdb.repository;
import org.springframework.data.repository.CrudRepository;

import com.lunatech.assessment.imdb.model.Principals;
import com.lunatech.assessment.imdb.model.PrincipalsId;

public interface PrincipalsRepository extends CrudRepository<Principals,PrincipalsId>{
    
}
