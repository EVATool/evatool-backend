package com.evatool.impact.domain.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public class AnalysisImpactId implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    private Integer numericId;


    public AnalysisImpactId() {

    }

    public AnalysisImpactId(Integer numericId) {
        this.numericId = numericId;
        //this.analysisId = analysisId;
    }
}
