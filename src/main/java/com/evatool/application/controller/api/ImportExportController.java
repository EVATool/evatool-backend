package com.evatool.application.controller.api;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

public interface ImportExportController {

    // TODO import...


    @PostMapping(produces = {"application/json"}) // TODO what is produced? File?
    @ApiOperation(value = "TODO")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request")})
    ResponseEntity<byte[]> exportAnalyses(); // TODO params

}
