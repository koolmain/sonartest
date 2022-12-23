package com.lunatech.assessment.imdb.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lunatech.assessment.imdb.model.Principals;
import com.lunatech.assessment.imdb.model.PrincipalsId;
import com.lunatech.assessment.imdb.repository.PrincipalsRepository;
import com.lunatech.assessment.imdb.service.PrincipalsService;

@Service
public class PrincipalsServiceImpl implements PrincipalsService{

    @Autowired
    private PrincipalsRepository principalsRepository; 
    
    
    /** 
     * Get Principals (relation between Name and TItle)
     * 
     * @param id PrincipalsId 
     * @return Optional<Principals>
     */
    @Override
    public Optional<Principals> getPrincipalsById(PrincipalsId id) {
        return principalsRepository.findById(id);
    }
    
}
