package com.lunatech.assessment.imdb.model.summary;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CollectionId;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


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
