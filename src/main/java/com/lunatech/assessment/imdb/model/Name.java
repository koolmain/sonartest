package com.lunatech.assessment.imdb.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="name_basics")
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
public class Name {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String nconst; 

    private String primaryName; 

    private Integer birthYear; 

    private Integer deathYear; 

    private String primaryProfession; 

    @OneToMany(
        mappedBy = "name1", 
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @JsonManagedReference
    private List<Principals> principalsList = new ArrayList<>();
}
