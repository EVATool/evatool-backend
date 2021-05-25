package com.evatool.application.service.api;

import com.evatool.application.dto.StakeholderDto;
import com.evatool.common.enums.StakeholderLevel;
import com.evatool.common.enums.StakeholderPriority;

public interface StakeholderService extends CrudService<StakeholderDto>, FindByAnalysisService<StakeholderDto> {

    Iterable<StakeholderLevel> findAllStakeholderLevels();

    Iterable<StakeholderPriority> findAllStakeholderPriorities();

}
