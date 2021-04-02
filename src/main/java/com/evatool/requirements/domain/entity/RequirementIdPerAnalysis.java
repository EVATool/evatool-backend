package com.evatool.requirements.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Table(name = "REQ_REQUIREMENTS_PER_ANALYSIS")
@Entity(name = "REQ_REQUIREMENTS_PER_ANALYSIS")
@EqualsAndHashCode
@ToString
public class RequirementIdPerAnalysis {



        @Id
        @Column(name = "ID", updatable = false, nullable = false)
        private UUID id=UUID.randomUUID();

        @Column(name = "ANALYSIS_ID", updatable = false, nullable = false)
        private String analysisId;

        @Column(name = "REQUIRMENTS_PER_ANALYSIS", nullable = false)
        private Integer requirmentsPerAnalysis;

        public UUID getId() {
                return id;
        }

        public void setId(UUID id) {
                this.id = id;
        }

        public String getAnalysisId() {
                return analysisId;
        }

        public void setAnalysisId(String analysisId) {
                this.analysisId = analysisId;
        }

        public Integer getRequirmentsPerAnalysis() {
                return requirmentsPerAnalysis;
        }

        public void setRequirmentsPerAnalysis(Integer requirmentsPerAnalysis) {
                this.requirmentsPerAnalysis = requirmentsPerAnalysis;
        }
}
