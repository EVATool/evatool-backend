package com.evatool.application.controller.api;

import com.evatool.application.dto.ExportAnalysisToPostmanDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.util.UUID;

public interface ExportController {

    @GetMapping(produces = {"application/json"})
    @ApiOperation(value = "Export analysis as postman script")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request")})
    ResponseEntity<ExportAnalysisToPostmanDto> exportAnalysisToPostman(@Valid @PathVariable UUID analysisId);

}
