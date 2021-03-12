package com.evatool.analysis.api.interfaces;


import com.evatool.analysis.dto.StakeholderDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@Api ("API-endpoint for Stakeholder")
public interface StakeholderController {

    @GetMapping("/stakeholder")
    @ApiOperation(value = "This method returns a list of stakeholder")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All entities returned")})
    public List<EntityModel<StakeholderDTO>> getStakeholderList();

    @GetMapping("/stakeholder/{id}")
    @ApiOperation(value = "This method returns an optional of an Stakeholder by his ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The entity was found"),
            @ApiResponse(code = 400, message = "The id was invalid"),
            @ApiResponse(code = 404, message = "The entity was not found")})
    public EntityModel<StakeholderDTO> getStakeholderById(@PathVariable UUID id);

    @PostMapping("/addStakeholder")
    @ApiOperation(value = "This Method add a Stakeholder")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The entity is inserted"),
            @ApiResponse(code = 400, message = "The entity is invalid"),
            @ApiResponse(code = 404, message = "The entity is not found")})
    public EntityModel<StakeholderDTO> addStakeholder(@RequestBody StakeholderDTO stakeholderDTO);

    @PutMapping("/stakeholder/{id}")
    @ApiOperation(value = "This method updated an stakeholder by his id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The entity is deleted"),
            @ApiResponse(code = 404, message = "The entity is not found")})
    public EntityModel<StakeholderDTO> updateStakeholder(@RequestBody StakeholderDTO stakeholderDTO);

    @DeleteMapping("/stakeholder/{id}")
    @ApiOperation(value = "This method delete an stakeholder by his id ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The entity is updated"),
            @ApiResponse(code = 400, message = "The entity is invalid"),
            @ApiResponse(code = 404, message = "The entity is not found")})
    public ResponseEntity<Void> deleteStakeholder(@PathVariable UUID id);

}
