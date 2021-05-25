package com.evatool.application.controller.api;

import com.evatool.application.dto.StakeholderDto;
import com.evatool.common.enums.StakeholderLevel;
import com.evatool.common.enums.StakeholderPriority;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface StakeholderController extends CrudController<StakeholderDto>, FindByAnalysisController<StakeholderDto> {

    @GetMapping(produces = {"application/json"})
    @ApiOperation(value = "Find all stakeholder levels")
    ResponseEntity<Iterable<StakeholderLevel>> findAllStakeholderLevels();

    @GetMapping(produces = {"application/json"})
    @ApiOperation(value = "Find all stakeholder levels")
    ResponseEntity<Iterable<StakeholderPriority>> findAllStakeholderPriorities();

}
