package com.evatool.application.controller.api;

import com.evatool.application.dto.AnalysisDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.core.io.InputStreamResource;
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
    ResponseEntity<Iterable<AnalysisDto>> importAnalyses(@RequestBody String importAnalyses);

    @GetMapping(produces = {"application/json"})
    @ApiOperation(value = "Export analyses by provided argument")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request")})
    ResponseEntity<InputStreamResource> exportAnalyses(@Valid @RequestParam UUID[] analysisIds, @RequestParam(required = false) String filename);

}
