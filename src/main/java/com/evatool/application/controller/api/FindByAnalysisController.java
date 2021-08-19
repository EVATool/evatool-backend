package com.evatool.application.controller.api;

import com.evatool.application.dto.AnalysisChildDto;
import com.evatool.application.service.api.FindByAnalysisService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.UUID;

public interface FindByAnalysisController<T extends AnalysisChildDto> {

    FindByAnalysisService getService();

    @GetMapping(produces = {"application/json"})
    @ApiOperation(value = "Find entity by analysis id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request")})
    default ResponseEntity<Iterable<T>> findAllByAnalysisId(@Valid @RequestParam UUID analysisId) {
        var dtoListFound = getService().findAllByAnalysisId(analysisId);
        return new ResponseEntity(dtoListFound, HttpStatus.OK);
    }
}
