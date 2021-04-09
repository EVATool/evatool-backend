package com.evatool.impact.application.service;


import com.evatool.impact.application.dto.ImpactRequirementDto;

import java.util.List;
import java.util.UUID;

public interface ImpactRequirementService {

    ImpactRequirementDto findById(UUID id);

    List<ImpactRequirementDto> findAll();

    void deleteAll();
}
