package com.evatool.application.service.api;

import com.evatool.application.dto.SuperDto;
import com.evatool.application.mapper.SuperMapper;
import com.evatool.application.service.TenancySentinel;
import com.evatool.domain.repository.FindByAnalysisRepository;

import java.util.UUID;

public interface FindByAnalysisService<T extends SuperDto> {

    SuperMapper getMapper();

    FindByAnalysisRepository getRepository();

    default Iterable<T> findAllByAnalysisId(UUID analysisId) {
        var entities = getRepository().findAllByAnalysisId(analysisId);
        entities = TenancySentinel.handleFind(entities);
        return getMapper().toDtoList(entities);
    }
}
