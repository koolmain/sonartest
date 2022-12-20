package com.lunatech.assessment.imdb.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

