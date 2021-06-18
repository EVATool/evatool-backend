package com.evatool.application.service.api;

import com.evatool.application.dto.ValueDto;
import com.evatool.common.enums.ValueType;

import java.util.UUID;

public interface ValueService extends CrudService<ValueDto>, FindByAnalysisService<ValueDto> {

    Iterable<ValueType> findAllValueTypes();

}
