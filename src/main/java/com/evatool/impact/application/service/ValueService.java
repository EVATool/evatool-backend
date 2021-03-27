package com.evatool.impact.application.service;


import com.evatool.impact.application.dto.ValueDto;
import com.evatool.impact.common.ValueType;
import com.evatool.impact.domain.entity.Value;

import java.util.List;
import java.util.UUID;

public interface ValueService {

    ValueDto findById(UUID id);

    List<ValueDto> findAllByType(ValueType valueType);

    List<ValueDto> findAll();

    List<ValueType> findAllTypes();

    ValueDto create(ValueDto valueDto);

    ValueDto update(ValueDto valueDto);

    void deleteById(UUID id);

    void deleteAll();
}
