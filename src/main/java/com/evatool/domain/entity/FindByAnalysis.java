package com.evatool.domain.entity;

public interface FindByAnalysis {

    Analysis getAnalysis();

    default void updateAnalysisLastUpdated() {
        getAnalysis().wasUpdated();
    }
}
