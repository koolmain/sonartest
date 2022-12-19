package com.lunatech.assessment.imdb.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ImdbConfigurations {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper(); 
    }
    
}
