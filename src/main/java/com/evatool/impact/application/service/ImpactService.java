package com.evatool.impact.application.service;

import com.evatool.impact.application.dto.ImpactDto;

import java.util.List;
import java.util.UUID;

public interface ImpactService {

    ImpactDto findById(UUID id);

    List<ImpactDto> findAll();

    List<ImpactDto> findAllByAnalysisId(UUID analysisId);

    ImpactDto create(ImpactDto impactDto);

    ImpactDto update(ImpactDto impactDto);

    void deleteById(UUID id);

    void deleteAll();
}
