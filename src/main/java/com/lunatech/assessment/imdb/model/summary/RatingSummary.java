package com.lunatech.assessment.imdb.model.summary;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public interface RatingSummary {

    String getTconst(); 

    Double getAverageRating(); 

    Integer getNumVotes(); 

    TitleSummary getTitle();
    
}
