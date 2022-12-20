package com.lunatech.assessment.imdb.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lunatech.assessment.imdb.dto.NameDTO;
import com.lunatech.assessment.imdb.dto.TitleDTO;
import com.lunatech.assessment.imdb.model.Name;
import com.lunatech.assessment.imdb.model.Title;

@Configuration
public class ImdbConfigurations {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper(); 
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        TypeMap<Title, TitleDTO> properyMapper = modelMapper.createTypeMap(Title.class, TitleDTO.class); 
        properyMapper.addMappings(mapper -> mapper.map(src -> src.getNames(), TitleDTO::setNames));

        return modelMapper; 
    }
    
}
