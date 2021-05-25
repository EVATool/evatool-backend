package com.evatool.application.service.api;

import com.evatool.application.dto.AnalysisDto;

import java.util.UUID;

public interface AnalysisService extends CrudService<AnalysisDto> {

    AnalysisDto deepCopy(UUID sourceAnalysisId, AnalysisDto analysisDto);

}
