package com.evatool.application.service.api;

import com.evatool.application.dto.SuperDto;
import com.evatool.application.mapper.SuperMapper;
import com.evatool.domain.repository.FindByAnalysisRepository;

import java.util.UUID;

public interface FindByAnalysisService<T extends SuperDto> {

    SuperMapper getMapper();

    FindByAnalysisRepository getRepository();

    default Iterable<T> findAllByAnalysisId(UUID analysisId) {
        return getMapper().toDtoList(getRepository().findAllByAnalysisId(analysisId));
    }
}
