package com.evatool.impact.application.service;


import com.evatool.impact.application.dto.ImpactValueDto;
import com.evatool.impact.common.ImpactValueType;

import java.util.List;
import java.util.UUID;

public interface ImpactValueService {

    ImpactValueDto findById(UUID id);

    List<ImpactValueDto> findAllByType(ImpactValueType impactValueType);

    List<ImpactValueDto> findAll();

    List<ImpactValueType> findAllTypes();

    void deleteAll();
}
