package com.lunatech.assessment.imdb.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.lunatech.assessment.imdb.model.Title;
import com.lunatech.assessment.imdb.repository.TitleRepository;
import com.lunatech.assessment.imdb.service.TitleService;


@Service
public class TitleServiceImpl implements TitleService {
    
    @Autowired
    private TitleRepository repository;

    @Override
    public Optional<Title> getTitleById(String id) {
        return repository.findById(id); 
    }

    @Override
    public List<Title> fetchTitleByGenre(String genre, int page) {
        Page<Title> titlesByGenre = repository.findTopRatedTitleByGenres(genre, PageRequest.of(page,5)); 
        List<Title> titles = titlesByGenre.getContent();
        return titles;
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
