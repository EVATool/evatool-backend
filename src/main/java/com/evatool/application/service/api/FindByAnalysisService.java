package com.evatool.application.service.api;

import com.evatool.application.dto.SuperDto;
import com.evatool.application.mapper.SuperMapper;
import com.evatool.application.service.TenantHandler;
import com.evatool.domain.repository.FindByAnalysisRepository;

import java.util.UUID;

public interface FindByAnalysisService<T extends SuperDto> {

    SuperMapper getMapper();

    FindByAnalysisRepository getRepository();

    default Iterable<T> findAllByAnalysisId(UUID analysisId) {
        var entities = getRepository().findAllByAnalysisId(analysisId);
        // TODO CrossRealmAccessException should be thrown here if analysisId is from different realm (add getAnalysisRepository to interface definition).
        entities = TenantHandler.handleFind(entities);
        var dto = getMapper().toDtoList(entities);
        return dto;
    }
}
