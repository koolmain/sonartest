package com.lunatech.assessment.imdb.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TitleDTO {

    String tconst; 
    String titleType; 
    String primaryTitle; 
    String originalTitle; 
    boolean isAdult; 
    int startYear; 
    int endYear; 
    int runTimeMinutes; 
    int averageRating; 
    long numVotes; 
    String directors; 
    String writes; 
    String genres; 

    List<NameDTO> names = new ArrayList<>(); 
    
}

