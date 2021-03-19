package com.evatool.impact.domain.entity;


import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

public class AnalysisImpactId implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Getter
    private Integer numericId;

    @Id
    private String analysisId = "lol";

    public AnalysisImpactId() {

    }

    public AnalysisImpactId(Integer numericId, String analysisId) {
        this.numericId = numericId;
        this.analysisId = analysisId;
    }
}
