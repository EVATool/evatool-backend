package com.evatool.analysis.application.controller;

import com.evatool.analysis.application.dto.ValueDto;
import com.evatool.analysis.application.services.ValueService;
import com.evatool.analysis.domain.enums.ValueType;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ValueControllerImpl {

    private static final Logger logger = LoggerFactory.getLogger(ValueControllerImpl.class);

    private final ValueService valueService;

    public ValueControllerImpl(ValueService valueService) {
        this.valueService = valueService;
    }

    @GetMapping("/values/{id}")
    @ApiOperation(value = "Read value by ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")})
    public ResponseEntity<EntityModel<ValueDto>> findById(@ApiParam("ImpactValue ID") @Valid @PathVariable UUID id) {
        logger.info("GET " + "/value/{id}");
        var valueDto = valueService.findById(id);
        return new ResponseEntity<>(getValueWithLinks(valueDto), HttpStatus.OK);
    }

    @GetMapping("/values")
    @ApiOperation(value = "Read all values")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request")})
    public ResponseEntity<List<EntityModel<ValueDto>>> findAll(@ApiParam(value = "ImpactValue Type") @Valid @RequestParam(value = "type", required = false) ValueType type) {
        List<ValueDto> valueDtoList;
        if (type == null) {
            logger.info("GET " + "/values");
            valueDtoList = valueService.findAll();
        } else {
            logger.info("GET " + "/values" + "?type={}", type);

            valueDtoList = valueService.findAllByType(type);
        }
        return new ResponseEntity<>(getValueWithLinks(valueDtoList), HttpStatus.OK);
    }

    @PostMapping("/values")
    @ApiOperation(value = "Create a new value")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 422, message = "Unprocessable")})
    public ResponseEntity<EntityModel<ValueDto>> create(@ApiParam("value") @Valid @RequestBody ValueDto valueDto) {
        logger.info("POST " + "/value");
        var insertedValueDto = valueService.create(valueDto);
        return new ResponseEntity<>(getValueWithLinks(insertedValueDto), HttpStatus.CREATED);
    }

    @PutMapping("/values")
    @ApiOperation(value = "Update a value")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Updated"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 422, message = "Unprocessable")})
    public ResponseEntity<EntityModel<ValueDto>> update(@ApiParam("ImpactValue") @Valid @RequestBody ValueDto valueDto) {
        logger.info("PUT " + "/value");
        var updatedValueDto = valueService.update(valueDto);
        return new ResponseEntity<>(getValueWithLinks(updatedValueDto), HttpStatus.OK);
    }

    @DeleteMapping("/values/{id}")
    @ApiOperation(value = "Delete value by ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Deleted"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")})
    public ResponseEntity<Void> deleteById(@ApiParam("ImpactValue ID") @Valid @PathVariable UUID id) {
        logger.info("DELETE " + "/values/{id}");
        valueService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/values/types")
    @ApiOperation(value = "Read all value types")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")})
    public ResponseEntity<List<ValueType>> findAllTypes() {
        logger.info("GET " + "/value/types");
        return new ResponseEntity<>(valueService.findAllTypes(), HttpStatus.OK);
    }

    private EntityModel<ValueDto> getValueWithLinks(ValueDto valueDto) {
        logger.debug("Adding HATEOAS Rest Level 3 links");
        var entityModel = EntityModel.of(valueDto);
        entityModel.add(linkTo(methodOn(ValueControllerImpl.class).findById(valueDto.getId())).withSelfRel());
        return entityModel;
    }

    private List<EntityModel<ValueDto>> getValueWithLinks(List<ValueDto> valueDtoList) {
        var entityModelList = new ArrayList<EntityModel<ValueDto>>();
        valueDtoList.forEach(valueDto -> entityModelList.add(getValueWithLinks(valueDto)));
        return entityModelList;
    }
}
