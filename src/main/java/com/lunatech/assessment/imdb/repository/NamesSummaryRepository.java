package com.lunatech.assessment.imdb.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.lunatech.assessment.imdb.model.Name;
import com.lunatech.assessment.imdb.model.summary.NameSmmary;

public interface NamesSummaryRepository extends CrudRepository<Name,String> {
    
    Optional<NameSmmary> findByNconst(String nconst);

    
}
