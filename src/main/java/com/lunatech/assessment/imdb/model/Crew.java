package com.lunatech.assessment.imdb.model;

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

@Entity
@Table(name ="title_crew")
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class Crew {
    @Id
    @Column(name = "tconst")
    String tconst; 

    @Column(name = "directors")
    String directors; 

    @Column(name = "writers")
    String writers; 

    @OneToOne
    @MapsId
    @JoinColumn(name = "tconst")
    @JsonBackReference
    private Title title;
    
}
