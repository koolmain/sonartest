package com.lunatech.assessment.imdb.repository;

import org.springframework.data.repository.CrudRepository;

import com.lunatech.assessment.imdb.model.Name;

public interface NamesRepository extends CrudRepository<Name,String> {
}
