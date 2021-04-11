package com.evatool.requirements.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;

@Table(name = "req_requirements_per_analysis")
@Entity(name = "req_requirements_per_analysis")
@EqualsAndHashCode
@ToString
public class RequirementIdPerAnalysis {



        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", updatable = false, nullable = false)
        private int id;

        @Column(name = "ANALYSIS_ID", updatable = false, nullable = false)
        private String analysisId;

        @Column(name = "REQUIRMENTS_PER_ANALYSIS", updatable = false, nullable = false)
        private Integer requirmentsPerAnalysis;

        public int getId() {
                return id;
        }

        public void setId(int id) {
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
