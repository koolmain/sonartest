package com.lunatech.assessment.imdb.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.lunatech.assessment.imdb.dto.TitleDTO;
import com.lunatech.assessment.imdb.model.Name;
import com.lunatech.assessment.imdb.model.Title;
import com.lunatech.assessment.imdb.repository.TitleRepository;
import com.lunatech.assessment.imdb.service.TitleService;


@Service
public class TitleServiceImpl implements TitleService {
    
    @Autowired
    private TitleRepository repository;

    @Autowired
    private ModelMapper modelMapper; 

    @Override
    public Optional<TitleDTO> getTitleById(String id) {
        Optional<Title> titleOptional = repository.findById(id); 
        return titleOptional
                .map(title -> {
                        List<Name> names = title.getPrincipalsList().stream().map(principal -> principal.getName1()).toList(); 
                        title.setNames(names);   
                        return title;
                    })
                .map(tile -> modelMapper.map(tile, TitleDTO.class)); 
    } 

    @Override
    public Optional<Title> getTitleWithPrincipalsById(String id) {
        Optional<Title> titleOptional = repository.findById(id); 
        return titleOptional
                .map(title -> {
                        List<Name> names = title.getPrincipalsList().stream().map(principal -> principal.getName1()).toList(); 
                        title.setNames(names);   
                        return title;
                    }); 
    }     

    @Override
    public List<TitleDTO> fetchTitleByGenre(String genre, int page) {
        Page<Title> titlesByGenre = repository.findTopRatedTitleByGenres(genre, PageRequest.of(page,5)); 
        return titlesByGenre.getContent().stream()
                    .map(title -> {
                        List<Name> names = title.getPrincipalsList().stream().map(principal -> principal.getName1()).toList(); 
                        title.setNames(names);   
                        return title;
                    })
                    .map(tile -> modelMapper.map(tile, TitleDTO.class)).toList();
                }

    @Override
    public List<Title> fetchTitlesByTitleName(String titleName, int page) {
        List<Title> titlesByTitleName = repository.findByOriginalTitleOrPrimaryTitleContainingIgnoreCase(titleName, titleName, PageRequest.of(page, 10));
        return titlesByTitleName;
    }

    @Override
    public List<Title> getAllTitlesFromIds(List<String> ids) {
        return repository.getAllTitlesFromIds(ids);
    }
}
