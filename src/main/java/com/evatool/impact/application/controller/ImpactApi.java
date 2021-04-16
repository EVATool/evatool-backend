package com.evatool.impact.application.controller;

import com.evatool.impact.application.dto.ImpactDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static com.evatool.impact.application.controller.UriUtil.IMPACTS;
import static com.evatool.impact.application.controller.UriUtil.IMPACTS_ID;

public interface ImpactApi {

    @GetMapping(value = IMPACTS_ID, produces = {"application/json"})
    @ApiOperation(value = "Read impact by ID", nickname = "findImpact", tags = "Impacts")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = ImpactDto.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")})
    ResponseEntity<EntityModel<ImpactDto>> findById(@ApiParam("Impact ID") @Valid @PathVariable UUID id);

    @GetMapping(value = IMPACTS, produces = {"application/json"})
    @ApiOperation(value = "Read all impacts", tags = "Impacts")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")})
    ResponseEntity<List<EntityModel<ImpactDto>>> findAll(@ApiParam(value = "Analysis Id") @Valid @RequestParam(value = "analysisId", required = false) UUID analysisId);

    @PostMapping(value = IMPACTS, consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Create a new impact", tags = "Impacts")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 422, message = "Unprocessable")})
    ResponseEntity<EntityModel<ImpactDto>> create(@ApiParam("Impact") @Valid @RequestBody ImpactDto impactDto);

    @PutMapping(value = IMPACTS, consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Update an impact", tags = "Impacts")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Updated"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 422, message = "Unprocessable")})
    ResponseEntity<EntityModel<ImpactDto>> update(@ApiParam("Impact") @Valid @RequestBody ImpactDto impactDto);

    @DeleteMapping(IMPACTS_ID)
    @ApiOperation(value = "Delete impact by ID", tags = "Impacts")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Deleted"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")})
    ResponseEntity<Void> deleteById(@ApiParam("Impact ID") @Valid @PathVariable UUID id);
}
