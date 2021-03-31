package com.evatool.impact.application.controller;

import com.evatool.impact.application.dto.ImpactDto;
import com.evatool.impact.application.dto.ImpactValueDto;
import com.evatool.impact.application.service.ImpactValueService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.evatool.impact.application.controller.UriUtil.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class ImpactValueRestController {

    private static final Logger logger = LoggerFactory.getLogger(ImpactValueRestController.class);

    private final ImpactValueService impactValueService;

    public ImpactValueRestController(ImpactValueService impactValueService) {
        this.impactValueService = impactValueService;
    }

    // TODO Tests
    @GetMapping(IMPACT_VALUE_ID)
    @ApiOperation(value = "Read impact value by ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")})
    public ResponseEntity<EntityModel<ImpactValueDto>> findById(@ApiParam("Impact Value ID") @Valid @PathVariable UUID id) {
        logger.info("GET " + IMPACT_VALUE_ID);
        var impactValueDto = impactValueService.findById(id);
        return new ResponseEntity<>(getImpactValueWithLinks(impactValueDto), HttpStatus.OK);
    }

    // TODO Tests
    @GetMapping(IMPACT_VALUES)
    @ApiOperation(value = "Read all impacts values by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")})
    public ResponseEntity<List<EntityModel<ImpactValueDto>>> findAll(@ApiParam(value = "Analysis Id") @Valid @RequestParam(value = "analysisId", required = true) UUID analysisId) {
        logger.info("GET " + IMPACT_VALUES);
        List<ImpactValueDto> impactValueDtoList;
        impactValueDtoList = impactValueService.findAllByAnalysisId(analysisId);
        return new ResponseEntity<>(getImpactValueWithLinks(impactValueDtoList), HttpStatus.OK);
    }

    // TODO Post

    // TODO Put

    // TODO Delete

    private EntityModel<ImpactValueDto> getImpactValueWithLinks(ImpactValueDto impactValueDto) {
        logger.debug("Adding HATEOAS Rest Level 3 links");
        var entityModel = EntityModel.of(impactValueDto);
        entityModel.add(linkTo(methodOn(ImpactValueRestController.class).findById(impactValueDto.getId())).withSelfRel());
        return entityModel;
    }

    private List<EntityModel<ImpactValueDto>> getImpactValueWithLinks(List<ImpactValueDto> impactValueDtoList) {
        var entityModelList = new ArrayList<EntityModel<ImpactValueDto>>();
        impactValueDtoList.forEach(impactValueDto -> entityModelList.add(getImpactValueWithLinks(impactValueDto)));
        return entityModelList;
    }
}
