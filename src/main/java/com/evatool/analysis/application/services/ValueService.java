package com.evatool.analysis.application.services;

import com.evatool.analysis.application.dto.ValueDto;
import com.evatool.analysis.domain.enums.ValueType;

import java.util.List;
import java.util.UUID;

public interface ValueService {

    ValueDto findById(UUID id);

    List<ValueDto> findAllByType(ValueType type);

    List<ValueDto> findAllByAnalysisId(UUID analysisId);

    List<ValueDto> findAll();

    List<ValueType> findAllTypes();

    ValueDto create(ValueDto valueDto);

    ValueDto update(ValueDto valueDto);

    void deleteById(UUID id);

    void deleteAll();
}
