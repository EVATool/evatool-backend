package com.evatool.application.controller.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.UUID;

public interface ImportExportController {

    @PostMapping(consumes = {"application/json"})
    @ApiOperation(value = "Import analyses from the provided argument")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request")})
    ResponseEntity<Void> importAnalyses(@RequestBody String importAnalyses);

    @GetMapping(produces = {"application/json"})
    @ApiOperation(value = "Export analyses by provided argument")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request")})
    ResponseEntity<byte[]> exportAnalyses(@Valid @RequestParam UUID[] analysisIds);

}
