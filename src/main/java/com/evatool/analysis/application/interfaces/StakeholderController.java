package com.evatool.analysis.application.interfaces;


import com.evatool.analysis.application.dto.StakeholderDTO;
import com.evatool.analysis.domain.enums.StakeholderLevel;
import io.swagger.annotations.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface StakeholderController {

    @GetMapping(value = "/stakeholders", produces = {"application/json"})
    @ApiOperation(value = "This method returns a list of stakeholder", nickname = "StakeholderAsList", tags = "Stakeholder")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All entities returned")})
    List<EntityModel<StakeholderDTO>> getStakeholderList();

    @GetMapping(value ="/stakeholders",params = "analysisId", produces = {"application/json"})
    @ApiOperation(value = "This method returns a list of stakeholder by analysisId", tags = "Stakeholder")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All entities returned")})
    List<EntityModel<StakeholderDTO>> getStakeholderByAnalysis(@ApiParam(value = "Stakeholder ID") @Valid @RequestParam("analysisId") UUID analysisId);


    @GetMapping(value = "/stakeholders/levels", produces = {"application/json"} )
    @ApiOperation(value = "This method returns a list of stakeholder levels", tags = "Stakeholder")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All levels returned")})
    public List<StakeholderLevel> findAllLevels();

    @GetMapping(value = "/stakeholders/{id}", produces = {"application/json"})
    @ApiOperation(value = "This method returns an optional of an Stakeholder by his ID", nickname = "findAnalysis", tags = "Analysis")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The entity was found"),
            @ApiResponse(code = 400, message = "The id was invalid"),
            @ApiResponse(code = 404, message = "The entity was not found")})
    EntityModel<StakeholderDTO> getStakeholderById(@ApiParam(value = "Stakeholder ID") @Valid @PathVariable UUID id);

    @PostMapping(value = "/stakeholders", consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "This Method add a Stakeholder", tags = "Stakeholder")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The entity is inserted"),
            @ApiResponse(code = 400, message = "The entity is invalid"),
            @ApiResponse(code = 404, message = "The entity is not found")})
    EntityModel<StakeholderDTO> addStakeholder(@ApiParam(value = "Stakeholder") @Valid @RequestBody StakeholderDTO stakeholderDTO);

    @PutMapping(value = "/stakeholders", produces = {"application/json"} )
    @ApiOperation(value = "This method updated an stakeholder by his id",tags = "Stakeholder")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The entity is deleted"),
            @ApiResponse(code = 404, message = "The entity is not found")})
    EntityModel<StakeholderDTO> updateStakeholder(@ApiParam(value = "Stakeholder") @Valid @RequestBody StakeholderDTO stakeholderDTO);

    @DeleteMapping(value = "/stakeholders/{id}", produces = {"application/json"})
    @ApiOperation(value = "This method delete an stakeholder by his id ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The entity is updated"),
            @ApiResponse(code = 400, message = "The entity is invalid"),
            @ApiResponse(code = 404, message = "The entity is not found")})
    ResponseEntity<Void> deleteStakeholder(@ApiParam(value = "Stakeholder ID") @Valid @PathVariable UUID id);
}
