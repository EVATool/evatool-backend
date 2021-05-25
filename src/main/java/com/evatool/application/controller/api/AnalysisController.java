package com.evatool.application.controller.api;

import com.evatool.application.dto.AnalysisDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

public interface AnalysisController extends CrudController<AnalysisDto> {

    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Find all analyses")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request")})
    ResponseEntity<Iterable<EntityModel<AnalysisDto>>> findAll();

    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Deep copy an analysis")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request")})
    ResponseEntity<EntityModel<AnalysisDto>> deepCopy(@Valid @PathVariable UUID templateAnalysisId, @Valid @RequestBody AnalysisDto analysisDto);

}
