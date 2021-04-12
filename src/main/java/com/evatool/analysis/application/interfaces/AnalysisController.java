package com.evatool.analysis.application.interfaces;

import com.evatool.analysis.application.dto.AnalysisDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api("API-endpoint for a analysis")
public interface AnalysisController {

    @GetMapping("/analysis")
    @ApiOperation(value = "This method returns a list of all analysis")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All entities returned")})
    List<EntityModel<AnalysisDTO>> getAnalysisList(@PathVariable Boolean isTemplate);

    @GetMapping("/analysis/{id}")
    @ApiOperation(value = "This method returns a value of an analysis by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The entity was found"),
            @ApiResponse(code = 400, message = "The id was invalid"),
            @ApiResponse(code = 404, message = "The entity was not found")})
    EntityModel<AnalysisDTO> getAnalysisById(@PathVariable UUID id);

    @PostMapping("/analysis")
    @ApiOperation(value = "This method add an analysis")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The entity is inserted"),
            @ApiResponse(code = 400, message = "The entity is invalid"),
            @ApiResponse(code = 404, message = "The entity is not found")})
    ResponseEntity<EntityModel<AnalysisDTO>> addAnalysis(@RequestBody AnalysisDTO analysisDTO);

    @PutMapping("/analysis")
    @ApiOperation(value = "This method updated an Analysis by his ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The entity is deleted"),
            @ApiResponse(code = 404, message = "The entity is not found")})
    EntityModel<AnalysisDTO> updateAnalysis(@RequestBody AnalysisDTO analysisDTO);

    @DeleteMapping("/analysis/{id}")
    @ApiOperation(value = "This method delete an analysis By his ID ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The entity is updated"),
            @ApiResponse(code = 400, message = "The entity is invalid"),
            @ApiResponse(code = 404, message = "The entity is not found")})
    ResponseEntity<Void> deleteAnalysis(@PathVariable UUID id);

    @PostMapping("/analysis/deep-copy/{id}")
    @ApiOperation(value = "This method deep copies an analysis with its values")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The entity is updated"),
            @ApiResponse(code = 400, message = "The entity is invalid"),
            @ApiResponse(code = 404, message = "The entity is not found")})
    ResponseEntity<EntityModel<AnalysisDTO>> deepCopyAnalysis(@PathVariable UUID id, @RequestBody AnalysisDTO analysisDTO);
}
