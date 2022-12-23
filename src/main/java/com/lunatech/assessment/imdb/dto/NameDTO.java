package com.lunatech.assessment.imdb.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NameDTO {
    String nconst; 
    String primaryName; 
    String birthYear; 
    String deathYear; 
    String primaryProfession; 
}
