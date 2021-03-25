package com.evatool.impact.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "IMP_IMPACTS_PER_ANALYSIS")
@Entity(name = "IMP_IMPACTS_PER_ANALYSIS")
@EqualsAndHashCode
@ToString
public class ImpactIdPerAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private int id;

    @Getter
    @Column(name = "ANALYSIS_ID", updatable = false, nullable = false)
    private String analysisId;

    @Getter
    @Column(name = "IMPACTS_PER_ANALYSIS", updatable = false, nullable = false)
    private Integer impactsPerAnalysis;
}
