package com.lunatech.assessment.imdb.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DegreeDto {
    int baconDegree; 
    List<DegreePathItem> path; 
}

