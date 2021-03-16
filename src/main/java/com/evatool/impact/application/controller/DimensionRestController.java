package com.evatool.impact.application.controller;

import com.evatool.impact.application.dto.DimensionDto;
import com.evatool.impact.application.service.DimensionService;
import com.evatool.impact.common.DimensionType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.evatool.impact.application.controller.UriUtil.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin
@RestController
public class DimensionRestController {

    private static final Logger logger = LoggerFactory.getLogger(DimensionRestController.class);

    private final DimensionService dimensionService;

    public DimensionRestController(DimensionService dimensionService) {
        this.dimensionService = dimensionService;
    }

    @GetMapping(DIMENSIONS_ID)
    @ApiOperation(value = "Read dimension by ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")})
    public ResponseEntity<EntityModel<DimensionDto>> findById(@ApiParam("Dimension ID") @Valid @PathVariable UUID id) {
        logger.info("GET " + DIMENSIONS_ID);
        var dimensionDto = dimensionService.findById(id);
        return new ResponseEntity<>(getDimensionWithLinks(dimensionDto), HttpStatus.OK);
    }

    @GetMapping(DIMENSIONS)
    @ApiOperation(value = "Read all dimensions")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<List<EntityModel<DimensionDto>>> findAll(@ApiParam(value = "Dimension Type") @Valid @RequestParam(value = "type", required = false) DimensionType type) {
        List<DimensionDto> dimensionDtoList;
        if (type == null) {
            logger.info("GET " + DIMENSIONS);
            dimensionDtoList = dimensionService.findAll();
        } else {
            logger.info("GET " + DIMENSIONS + "?type={}", type);
            dimensionDtoList = dimensionService.findAllByType(type);
        }
        return new ResponseEntity<>(getDimensionsWithLinks(dimensionDtoList), HttpStatus.OK);
    }

    @PostMapping(DIMENSIONS)
    @ApiOperation(value = "Create a new dimension")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 422, message = "Unprocessable")})
    public ResponseEntity<EntityModel<DimensionDto>> create(@ApiParam("Dimension") @Valid @RequestBody DimensionDto dimensionDto) {
        logger.info("POST " + DIMENSIONS);
        var insertedDimensionDto = dimensionService.create(dimensionDto);
        return new ResponseEntity<>(getDimensionWithLinks(insertedDimensionDto), HttpStatus.CREATED);
    }

    @PutMapping(DIMENSIONS)
    @ApiOperation(value = "Update a dimension")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Updated"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 422, message = "Unprocessable")})
    public ResponseEntity<EntityModel<DimensionDto>> update(@ApiParam("Dimension") @Valid @RequestBody DimensionDto dimensionDto) {
        logger.info("PUT " + DIMENSIONS);
        var updatedDimensionDto = dimensionService.update(dimensionDto);
        return new ResponseEntity<>(getDimensionWithLinks(updatedDimensionDto), HttpStatus.OK);
    }

    @DeleteMapping(DIMENSIONS_ID)
    @ApiOperation(value = "Delete dimension by ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Deleted"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")})
    public ResponseEntity<Void> deleteById(@ApiParam("Dimension ID") @Valid @PathVariable UUID id) {
        logger.info("DELETE " + DIMENSIONS_ID);
        dimensionService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(DIMENSION_TYPES)
    @ApiOperation(value = "Read all dimension types")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<List<DimensionType>> findAllTypes() {
        logger.info("GET " + DIMENSION_TYPES);
        return new ResponseEntity<>(dimensionService.findAllTypes(), HttpStatus.OK);
    }

    private EntityModel<DimensionDto> getDimensionWithLinks(DimensionDto dimensionDto) {
        logger.debug("Adding HATEOAS Rest Level 3 links");
        var entityModel = EntityModel.of(dimensionDto);
        entityModel.add(linkTo(methodOn(DimensionRestController.class).findById(dimensionDto.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(DimensionRestController.class).update(dimensionDto)).withRel(UPDATE_DIMENSION));
        entityModel.add(linkTo(methodOn(DimensionRestController.class).deleteById(dimensionDto.getId())).withRel(DELETE_DIMENSION));
        return entityModel;
    }

    private List<EntityModel<DimensionDto>> getDimensionsWithLinks(List<DimensionDto> dimensionDtoList) {
        var entityModelList = new ArrayList<EntityModel<DimensionDto>>();
        dimensionDtoList.forEach(dimensionDto -> entityModelList.add(getDimensionWithLinks(dimensionDto)));
        return entityModelList;
    }
}
