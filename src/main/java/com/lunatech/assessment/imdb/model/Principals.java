package com.lunatech.assessment.imdb.model;

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

@Entity
@Table(name="title_principals")
@Getter
@Setter
@ToString
//@JsonIgnoreProperties(value = {"handler","hibernateLa"})
public class Principals{

    @EmbeddedId
    private PrincipalsId id; 

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("tconstId")
    @JoinColumn(name = "tconst")
    @JsonBackReference
    private Title title1; 

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("nconstId")
    @JoinColumn(name = "nconst")
    @JsonBackReference
    private Name name1; 

    private String category; 

    private String job; 

    private String characters; 
}