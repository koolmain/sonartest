package com.lunatech.assessment.imdb.model.summary;

import java.util.List;

public interface TitleSummary {


    String getTconst(); 

    String getTitleType(); 

    String getPrimaryTitle(); 

    String getOriginalTitle(); 

    boolean isAdult(); 

    Integer getStartYear(); 

    Integer getEndYear(); 

    Integer getRuntimeMinutes(); 

    String getGenres();   

    RatingSummary getRating();   

    CrewSummary getCrew();   
    
    List<PrincipalSummary> getPrincipalsList(); 
}
