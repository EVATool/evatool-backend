package com.evatool.application.controller.api;

import com.evatool.application.dto.ValueDto;
import com.evatool.common.enums.ValueType;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public interface ValueController extends CrudController<ValueDto>, FindByAnalysisController<ValueDto> {

    @GetMapping(produces = {"application/json"})
    @ApiOperation(value = "Find all values types")
    ResponseEntity<Iterable<ValueType>> findAllValuesTypes();

}
