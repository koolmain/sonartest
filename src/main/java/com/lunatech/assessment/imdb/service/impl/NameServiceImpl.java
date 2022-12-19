package com.lunatech.assessment.imdb.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lunatech.assessment.imdb.model.Name;
import com.lunatech.assessment.imdb.model.summary.NameSmmary;
import com.lunatech.assessment.imdb.repository.NamesRepository;
import com.lunatech.assessment.imdb.repository.NamesSummaryRepository;
import com.lunatech.assessment.imdb.service.NameService;

@Service
public class NameServiceImpl implements NameService {

    @Autowired
    private NamesSummaryRepository namesSummaryRepository;  

    @Autowired
    private NamesRepository namesRepository; 

    @Override
    public Optional<Name> getNameById(String id) {
        return namesRepository.findById(id);
    }

    @Override
    public Optional<NameSmmary> getNameSummaryById(String id) {
        return namesSummaryRepository.findByNconst(id);
    }
}

