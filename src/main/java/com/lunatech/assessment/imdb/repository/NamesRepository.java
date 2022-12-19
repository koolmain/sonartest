package com.lunatech.assessment.imdb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.lunatech.assessment.imdb.model.Name;
import com.lunatech.assessment.imdb.model.summary.NameSmmary;

public interface NamesRepository extends CrudRepository<Name,String> {
}
