package com.evatool.application.controller.api;

import com.evatool.application.dto.ValueDto;
import com.evatool.common.enums.ValueType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

public interface ValueController extends CrudController<ValueDto>, FindByAnalysisController<ValueDto> {

    @GetMapping(produces = {"application/json"})
    @ApiOperation(value = "Find all values types")
    ResponseEntity<Iterable<ValueType>> findAllValuesTypes();

}
