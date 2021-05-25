package com.evatool.domain.repository;

import com.evatool.domain.entity.FindByAnalysis;

import java.util.UUID;

public interface FindByAnalysisRepository<T extends FindByAnalysis> {

    Iterable<T> findAllByAnalysisId(UUID analysisId);

}
