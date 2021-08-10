package com.evatool.application.service.api;

import com.evatool.application.dto.SuperDto;
import com.evatool.application.dto.mapper.SuperDtoMapper;
import com.evatool.application.service.TenancySentinel;
import com.evatool.domain.repository.FindByAnalysisRepository;

import java.util.UUID;

public interface FindByAnalysisService<T extends SuperDto> {

    SuperDtoMapper getMapper();

    FindByAnalysisRepository getRepository();

    default Iterable<T> findAllByAnalysisId(UUID analysisId) {
        var entities = getRepository().findAllByAnalysisId(analysisId);
        // TODO CrossRealmAccessException should be thrown here if analysisId is from different realm (add getAnalysisRepository to interface definition).
        entities = TenancySentinel.handleFind(entities);
        var dto = getMapper().toDtoList(entities);
        return dto;
    }
}
