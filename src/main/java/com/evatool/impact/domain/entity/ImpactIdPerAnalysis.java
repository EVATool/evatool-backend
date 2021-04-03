package com.evatool.impact.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "imp_impacts_per_analysis")
@Entity(name = "imp_impacts_per_analysis")
@EqualsAndHashCode
@ToString
public class ImpactIdPerAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Getter
    @Column(name = "analysis_id", updatable = false, nullable = false)
    private String analysisId;

    @Getter
    @Column(name = "impacts_per_analysis", updatable = false, nullable = false)
    private Integer impactsPerAnalysis;
}
