package com.evatool.variants.application.controller;

import com.evatool.variants.application.dto.VariantDto;
import com.evatool.variants.application.services.VariantService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.evatool.variants.application.services.VariantsUriHelper.VARIANTS;
import static com.evatool.variants.application.services.VariantsUriHelper.VARIANTS_ID;


@Api(value = "VariantsController", tags = "variants")
@RestController
public class VariantController {

    final Logger logger = LoggerFactory.getLogger(VariantController.class);

    @Autowired
    VariantService variantService;

    @ApiOperation(value = "postVariant", notes = "post a new variant", nickname = "postVariant")
    @ApiResponse(code = 201, message = "Created successfully", response = VariantDto.class, responseContainer = "Variant")
    @PostMapping(VARIANTS)
    public ResponseEntity<VariantDto> createVariant(@RequestBody VariantDto newVariantDto) {
        logger.info("[POST] /variants");
        return new ResponseEntity<>(variantService.createVariant(newVariantDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "getVariant", notes = "get a variant", nickname = "getVariant")
    @ApiResponse(code = 201, message = "Created successfully", response = VariantDto.class, responseContainer = "Variant")
    @GetMapping(VARIANTS_ID)
    public ResponseEntity<VariantDto> getVariant(
            @ApiParam(name = "variantId", value = "identification of a Variant", required = true)
            @PathVariable UUID id) {
        logger.info("[GET] /variants/{id}");
        return new ResponseEntity<>(variantService.getVariant(id), HttpStatus.OK);
    }

    @ApiOperation(value = "getVariantsByAnalysis", notes = "get list of all variants by analysis id", nickname = "getVariantsByAnalysis")
    @ApiResponse(code = 201, message = "Created successfully", response = VariantDto.class, responseContainer = "Variant")
    @GetMapping(value = VARIANTS,params = "analysisId")
    public ResponseEntity<CollectionModel<VariantDto>> getVariantsByAnalysis(@RequestParam("analysisId") UUID analysisId){
        logger.info("[GET] /variants?analysisId={id}");
        return new ResponseEntity<>(variantService.getVariantsByAnalysis(analysisId), HttpStatus.OK);
    }

    @ApiOperation(value = "getAllVariants", notes = "get list of all variants", nickname = "getAllVariants")
    @ApiResponse(code = 200, message = "Successful retrieval", response = VariantDto.class, responseContainer = "List")
    @GetMapping(VARIANTS)
    public ResponseEntity<CollectionModel<VariantDto>> getAllVariants() {
        logger.info("[GET] /variants");
        return new ResponseEntity<>(variantService.getAllVariants(), HttpStatus.OK);
    }

    @ApiOperation(value = "putVariants", notes = "put a variants", nickname = "putVariant")
    @ApiResponse(code = 200, message = "Successful changed", response = VariantDto.class, responseContainer = "List")
    @PutMapping(VARIANTS_ID)
    public ResponseEntity<VariantDto> updateVariant(
            @ApiParam(name = "variantId", value = "identification of a Variant", required = true)
            @RequestBody VariantDto updatedVariantDto) {
        logger.info("[PUT] /variants");
        return new ResponseEntity<>(variantService.updateVariant(updatedVariantDto), HttpStatus.OK);
    }

    @ApiOperation(value = "deleteVariants", notes = "delete a variants", nickname = "deleteVariant")
    @ApiResponse(code = 200, message = "Successful deleted", response = VariantDto.class, responseContainer = "List")
    @DeleteMapping(value = VARIANTS_ID)
    public ResponseEntity<Void> deleteVariant(
            @ApiParam(name = "variantId", value = "identification of a Variant", required = true)
            @PathVariable UUID id) {
        logger.info("[DELETE] /variants/{id}");
        variantService.deleteVariant(id);
        return ResponseEntity.ok().build();
    }
}
