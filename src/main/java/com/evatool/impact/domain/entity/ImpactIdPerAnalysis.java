package com.evatool.impact.domain.entity;

import lombok.Getter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Table(name = "IMP_IMPACTS_PER_ANALYSIS")
@Entity(name = "IMP_IMPACTS_PER_ANALYSIS")
public class ImpactIdPerAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", updatable = false, nullable = false)
    private int id;

    @Getter
    @Type(type = "uuid-char")
    @Column(name = "ANALYSIS_ID", updatable = false, nullable = false)
    private String analysisId;
    //@Column(name = "ANALYSIS_ID", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    //private UUID analysisId;

    @Getter
    @Column(name = "IMPACTS_PER_ANALYSIS", updatable = false, nullable = false)
    private Integer impactsPerAnalysis;
}
