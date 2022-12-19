package com.lunatech.assessment.imdb.model.summary;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


public interface PrincipalSummary{

    PrincipalsIdSummary getId(); 

    TitleSummary getTitle1(); 

    NameSmmary getName1(); 

    String getCategory(); 

    String getJob(); 

    String getCharacters(); 
}