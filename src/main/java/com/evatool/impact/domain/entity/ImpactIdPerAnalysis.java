package com.evatool.impact.domain.entity;

import lombok.Getter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "IMP_IMPACT_ID_PER_ANALYSIS")
@Entity(name = "IMP_IMPACT_ID_PER_ANALYSIS")
public class ImpactIdPerAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Getter
    @Type(type = "uuid-char")
    @Column(name = "ANALYSIS_ID", updatable = false, nullable = false, columnDefinition = "CHAR(36)")
    private UUID analysisId;

    @Getter
    @Column(name = "IMPACTS_PER_ANALYSIS", updatable = false, nullable = false)
    private Integer impactsPerAnalysis;
}
