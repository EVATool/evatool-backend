package com.evatool.application.service.api;

import com.evatool.application.dto.RequirementDeltaDto;
import com.evatool.domain.entity.RequirementDelta;

import java.util.UUID;

public interface RequirementDeltaService extends CrudService<RequirementDeltaDto>, FindByAnalysisService<RequirementDeltaDto> {

}
