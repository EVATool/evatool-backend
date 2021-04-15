package com.evatool.analysis.application.interfaces;

import com.evatool.analysis.application.dto.AnalysisDTO;
import io.swagger.annotations.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface AnalysisController {

    @GetMapping(value = "/analysis", produces = {"application/json"})
    @ApiOperation(value = "This method returns a list of all analysis", nickname = "AnalysesAsList", tags = "Analysis")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All entities returned")})
    List<EntityModel<AnalysisDTO>> getAnalysisList(@ApiParam(value = "Is Template?") @Valid @PathVariable Boolean isTemplate);

    @GetMapping(value = "/analysis/{id}", produces = {"application/json"})
    @ApiOperation(value = "This method returns a value of an analysis by ID", nickname = "findAnalysis", tags = "Analysis")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The entity was found"),
            @ApiResponse(code = 400, message = "The id was invalid"),
            @ApiResponse(code = 404, message = "The entity was not found")})
    EntityModel<AnalysisDTO> getAnalysisById(@ApiParam(value = "Analysis ID") @Valid @PathVariable UUID id);

    @PostMapping(value = "/analysis", consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "This method add an analysis", tags = "Analysis")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The entity is inserted"),
            @ApiResponse(code = 400, message = "The entity is invalid"),
            @ApiResponse(code = 404, message = "The entity is not found")})
    ResponseEntity<EntityModel<AnalysisDTO>> addAnalysis(@ApiParam(value = "Analysis") @Valid @RequestBody AnalysisDTO analysisDTO);

    @PutMapping(value = "/analysis", consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "This method updated an Analysis by his ID", tags = "Analysis")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The entity is deleted"),
            @ApiResponse(code = 404, message = "The entity is not found")})
    EntityModel<AnalysisDTO> updateAnalysis(@ApiParam(value = "Analysis") @Valid @RequestBody AnalysisDTO analysisDTO);

    @DeleteMapping(value = "/analysis/{id}")
    @ApiOperation(value = "This method delete an analysis By his ID ", tags = "Analysis")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The entity is updated"),
            @ApiResponse(code = 400, message = "The entity is invalid"),
            @ApiResponse(code = 404, message = "The entity is not found")})
    ResponseEntity<Void> deleteAnalysis(@ApiParam("Analysis ID")@PathVariable UUID id);

    @PostMapping("/analysis/deep-copy/{id}")
    @ApiOperation(value = "This method deep copies an analysis with its values", tags = "Analysis")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The entity is updated"),
            @ApiResponse(code = 400, message = "The entity is invalid"),
            @ApiResponse(code = 404, message = "The entity is not found")})
    ResponseEntity<EntityModel<AnalysisDTO>> deepCopyAnalysis(@PathVariable UUID id, @RequestBody AnalysisDTO analysisDTO);
}
