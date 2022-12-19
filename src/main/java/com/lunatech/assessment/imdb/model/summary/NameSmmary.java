package com.lunatech.assessment.imdb.model.summary;

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


public interface NameSmmary {
    
    String getNconst(); 

    String getPrimaryName(); 

    Integer getBirthYear(); 

    String getPrimaryProfession(); 
}
