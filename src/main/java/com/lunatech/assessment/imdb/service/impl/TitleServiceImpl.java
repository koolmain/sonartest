package com.lunatech.assessment.imdb.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.lunatech.assessment.imdb.dto.TitleDTO;
import com.lunatech.assessment.imdb.model.Principals;
import com.lunatech.assessment.imdb.model.Title;
import com.lunatech.assessment.imdb.model.summary.TitleSummary;
import com.lunatech.assessment.imdb.repository.TitleRepository;
import com.lunatech.assessment.imdb.repository.TitleSummaryRepository;
import com.lunatech.assessment.imdb.service.TitleService;


@Service
public class TitleServiceImpl implements TitleService {
    
    @Autowired
    private TitleRepository repository;

    @Autowired
    private TitleSummaryRepository titleSummaryRepository;

    @Autowired
    private ModelMapper modelMapper; 

    
    /** 
     * Get title by its 'id'.
     * Results are cached and retrun from cache when call with same id happended again. 
     * 
     * Results are cached under 'titlesForUI' cache. 
     * 
     * @param id
     * @return Optional<TitleDTO>
     */
    @Cacheable("titlesForUI")
    @Override
    public Optional<TitleDTO> getTitleById(String id) {
        return getTitleWithPrincipalsById(id)
                .map(tile -> modelMapper.map(tile, TitleDTO.class)); 
    } 

    
    /** 
     * Get Title with principals 
     * Results are cached under 'titlesById' cache. 
     * 
     * @param id
     * @return Optional<Title>
     */
    @Cacheable("titlesById")
    @Override
    public Optional<Title> getTitleWithPrincipalsById(String id) {
        return repository.findById(id).map(this::fillNamesInTitle); 
    }     

    
    /** 
     * Fetch title by its 'genre'. 
     * Results are paged. 
     * 
     * @param genre
     * @param page
     * @return List<TitleDTO>
     */
    @Override
    public List<TitleDTO> fetchTitleByGenre(String genre, int page) {
        Page<Title> titlesByGenre = repository.findTopRatedTitleByGenres(genre, PageRequest.of(page,5)); 
        return titlesByGenre.getContent().stream()
                    .map(this::fillNamesInTitle)
                    .map(tile -> modelMapper.map(tile, TitleDTO.class)).toList();
                }


    
    /** 
     * Fetch Names using its relation table Principals and add all linked Names in Title. 
     * 
     * @param title
     * @return Title
     */
    private Title fillNamesInTitle(Title title) {
        title.setNames(title.getPrincipalsList().stream().map(Principals::getName1).toList());   
        return title;
    }

    
    /** 
     * Fetch title by its 'name'.
     * 
     * Results are paged.  
     * 
     * @param titleName
     * @param page
     * @return List<TitleDTO>
     */
    @Override
    public List<TitleDTO> fetchTitlesByTitleName(String titleName, int page) {
        List<Title> titlesByTitleName = repository.findByOriginalTitleOrPrimaryTitleContainingIgnoreCase(titleName, titleName, PageRequest.of(page, 10));
        return titlesByTitleName.stream()
                    .map(this::fillNamesInTitle)
                    .map(tile -> modelMapper.map(tile, TitleDTO.class)).toList();
    }

    
    /** 
     * Get all Titles list whose ids are in given 'ids' list. 
     * 
     * @param ids
     * @return List<Title>
     */
    @Override
    public List<Title> getAllTitlesFromIds(List<String> ids) {
        return repository.getAllTitlesFromIds(ids);
    }

    
    /** 
     * Get TitleSummary from given 'id'.
     * 
     * @param id
     * @return Optional<TitleSummary>
     */
    @Override
    public Optional<TitleSummary> getTitleSummaryById(String id) {
        return titleSummaryRepository.findByTconst(id);
    }
}
