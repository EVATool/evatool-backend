package com.evatool.impact.application.service;


import com.evatool.impact.application.dto.ImpactValueDto;

import java.util.List;
import java.util.UUID;

public interface ImpactValueService {

    ImpactValueDto findById(UUID id);

    List<ImpactValueDto> findAllByAnalysisId(UUID analysisId);

    List<ImpactValueDto> findAll();

    void deleteAll();
}
