package com.lunatech.assessment.imdb.model;

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
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "title_basics")
@Getter
@Setter
@ToString
public class Title {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String tconst; 

    @Column(name="titleType")
    private String titleType; 

    @Column(name="primaryTitle")
    private String primaryTitle; 

    @Column(name="originalTitle")
    private String originalTitle; 

    @Column(name="isAdult")
    private boolean isAdult; 

    @Column(name="startYear")
    private Integer startYear; 

    @Column(name="endYear")
    private Integer endYear; 

    @Column(name="runtimeMinutes")
    private Integer runtimeMinutes; 

    private String genres;   

    @OneToOne(mappedBy = "title", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonManagedReference
    private Rating rating;   

    @OneToOne(mappedBy = "title", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @JsonManagedReference
    private Crew crew;   
    
    @OneToMany(
        mappedBy = "title1", 
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @JsonManagedReference
    private List<Principals> principalsList = new ArrayList<>(); 

    @Transient
    private List<Name> names = new ArrayList<>(); 
}
