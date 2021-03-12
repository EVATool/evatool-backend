package com.evatool.impact.application.service;

import com.evatool.impact.application.dto.ImpactAnalysisDto;

import java.util.List;
import java.util.UUID;

public interface ImpactAnalysisService {

    ImpactAnalysisDto findById(UUID id);

    List<ImpactAnalysisDto> findAll();

    void deleteAll();
}
