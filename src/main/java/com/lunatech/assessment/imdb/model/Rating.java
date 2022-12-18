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
@Table(name="title_ratings")
@Getter
@Setter
@ToString
public class Rating {

    @Id
    @Column(name = "tconst")
    private String tconst; 

    private Double averageRating; 

    @Column(name = "numVotes")
    private Integer numVotes; 

    @OneToOne
    @MapsId
    @JoinColumn(name = "tconst")
    @JsonBackReference
    private Title title;
    
}
