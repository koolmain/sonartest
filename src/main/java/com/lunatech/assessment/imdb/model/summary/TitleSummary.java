package com.lunatech.assessment.imdb.model.summary;

import java.util.List;

public interface TitleSummary {

    String getTconst(); 

    String getTitleType(); 

    String getOriginalTitle(); 

    boolean isAdult(); 

    Integer getStartYear(); 

    String getGenres();   

    RatingSummary getRating();   
    
}
