package com.evatool.variants.application.controller;


import com.evatool.impact.application.dto.ImpactDto;
import com.evatool.variants.application.dto.VariantDto;
import com.evatool.variants.application.services.VariantService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.evatool.variants.application.services.VariantsUriHelper.VARIANTS;
import static com.evatool.variants.application.services.VariantsUriHelper.VARIANTS_ID;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class VariantController {

    final Logger logger = LoggerFactory.getLogger(VariantController.class);

    @Autowired
    VariantService variantService;

    @ApiOperation(value = "Create a new variant", nickname = "postVariant", tags = "Variants")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 422, message = "Unprocessable")})
    @PostMapping(value = VARIANTS, consumes = {"application/json"} , produces = {"application/json"})
    public ResponseEntity<EntityModel<VariantDto>> createVariant(@RequestBody VariantDto newVariantDto) {
        logger.info("[POST] /variants");
        return new ResponseEntity<>(generateLinks(variantService.createVariant(newVariantDto)), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Read variant by ID", nickname = "getVariant", tags = "Variants")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = VariantDto.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")})
    @GetMapping(value = VARIANTS_ID, produces = {"application/json"})
    public ResponseEntity<EntityModel<VariantDto>> getVariant(
            @ApiParam(name = "variantId", value = "identification of a Variant", required = true)
            @PathVariable UUID id) {
        logger.info("[GET] /variants/{id}");
        return new ResponseEntity<>(generateLinks(variantService.getVariant(id)), HttpStatus.OK);
    }

    @ApiOperation(value = "Read variant by analysis ID", nickname = "getVariantsByAnalysis", tags = "Variants")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = VariantDto.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")})
    @GetMapping(value = VARIANTS, params = "analysisId", produces = {"application/json"})
    public ResponseEntity<List<EntityModel<VariantDto>>> getVariantsByAnalysis(@RequestParam("analysisId") UUID analysisId){
        logger.info("[GET] /variants?analysisId={id}");
        return new ResponseEntity<>(generateLinks(variantService.getVariantsByAnalysis(analysisId)), HttpStatus.OK);
    }

    @ApiOperation(value = "Read all variants", nickname = "getAllVariants", tags = "Variants")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK", response = VariantDto.class),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")})
    @GetMapping(value = VARIANTS, produces = {"application/json"})
    public ResponseEntity<List<EntityModel<VariantDto>>> getAllVariants() {
        logger.info("[GET] /variants");

        return new ResponseEntity<>(generateLinks(variantService.getAllVariants()), HttpStatus.OK);
    }

    @ApiOperation(value = "Update an variant", nickname = "putVariant", tags = "Variants")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Updated"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 422, message = "Unprocessable")})
    @PutMapping(value = VARIANTS, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<EntityModel<VariantDto>> updateVariant(
            @ApiParam(name = "variantId", value = "identification of a Variant", required = true)
            @RequestBody VariantDto updatedVariantDto) {
        logger.info("[PUT] /variants");
        return new ResponseEntity<>(generateLinks(variantService.updateVariant(updatedVariantDto)), HttpStatus.OK);
    }

    @ApiOperation(value = "Delete variant by ID", nickname = "deleteVariant", tags = "Variants")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Deleted"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "Not Found")})
    @DeleteMapping(value = VARIANTS_ID, produces = {"application/json"})
    public ResponseEntity<Void> deleteVariant(
            @ApiParam(name = "variantId", value = "identification of a Variant", required = true)
            @PathVariable UUID id) {
        logger.info("[DELETE] /variants/{id}");
        variantService.deleteVariant(id);
        return ResponseEntity.ok().build();
    }


    private EntityModel<VariantDto> generateLinks(VariantDto newVariantDto){
        EntityModel<VariantDto> variantDtoEntityModel = EntityModel.of(newVariantDto);
        variantDtoEntityModel.add(linkTo(methodOn(VariantController.class).getVariant(newVariantDto.getId())).withSelfRel());
        variantDtoEntityModel.add(linkTo(VariantController.class).slash("analysis").slash(newVariantDto.getAnalysisId()).withRel("analysis"));
        return variantDtoEntityModel;
    }

    private List<EntityModel<VariantDto>> generateLinks(List<VariantDto> newVariantDTOList){
        List<EntityModel<VariantDto>> returnList = new ArrayList<>();
        newVariantDTOList.stream().forEach(element -> returnList.add(generateLinks(element)));
        return returnList;
    }
}
