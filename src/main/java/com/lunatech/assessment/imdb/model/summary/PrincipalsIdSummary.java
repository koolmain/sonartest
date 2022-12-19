package com.lunatech.assessment.imdb.model.summary;


import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



public interface PrincipalsIdSummary {

    String getTconstId(); 

    Integer getOrdering(); 

    String getNconstId();    
}
