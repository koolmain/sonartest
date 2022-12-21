package com.lunatech.assessment.imdb.model.summary;


public interface TitleSummary {

    String getTconst(); 

    String getTitleType(); 

    String getOriginalTitle(); 

    boolean isIsAdult(); 

    Integer getStartYear(); 

    String getGenres();   

    RatingSummary getRating();   
    
}
