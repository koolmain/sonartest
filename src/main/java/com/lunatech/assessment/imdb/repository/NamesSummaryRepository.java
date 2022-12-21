package com.lunatech.assessment.imdb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.lunatech.assessment.imdb.model.Name;
import com.lunatech.assessment.imdb.model.summary.NameSmmary;

public interface NamesSummaryRepository extends CrudRepository<Name,String> {
    
    Optional<NameSmmary> findByNconst(String nconst);

    @Query(value = "select name from Name name where name.nconst in :idList")
    List<NameSmmary> getAllNamesFromIds(List<String> idList); 

    
}
