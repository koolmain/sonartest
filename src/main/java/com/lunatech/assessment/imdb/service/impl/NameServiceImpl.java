package com.lunatech.assessment.imdb.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    
    /** 
     * Get Name by id (nconst)
     * 
     * Results are cached by id under 'namesById'. If method is called again with same id 
     * then result would be produced by cache and not calling by function again. 
     * @param id
     * @return Optional<Name>
     */
    @Cacheable("namesById")
    @Override
    public Optional<Name> getNameById(String id) {
        return namesRepository.findById(id);
    }

    
    /** 
     * Get NameSummary by id (nconst)
     * 
     * Results are cached by id under 'titlesForUI' . If method is called again with same id 
     * then result would be produced by cache and not calling by function again. 
     * 
     * @param id
     * @return Optional<NameSmmary>
     */
    @Cacheable("titlesForUI")
    @Override
    public Optional<NameSmmary> getNameSummaryById(String id) {
        return namesSummaryRepository.findByNconst(id);
    }
}

