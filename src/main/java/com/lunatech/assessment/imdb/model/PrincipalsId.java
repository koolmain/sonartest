package com.lunatech.assessment.imdb.model;


import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Embeddable
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PrincipalsId implements Serializable{

    @Column(name = "tconst")
    private String tconstId; 

    @Column(name = "ordering")
    private Integer ordering; 

    @Column(name = "nconst")
    private String nconstId;    
}
