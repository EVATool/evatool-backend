package com.evatool.application.controller.impl;

import com.evatool.application.controller.UriUtil;
import com.evatool.application.controller.api.ExportController;
import com.evatool.application.dto.ExportAnalysisToPostmanDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

public class ExportControllerImpl implements ExportController {

    @Override
    @GetMapping(UriUtil.EXPORT_ANALYSIS_AS_POSTMAN_ID)
    public ResponseEntity<ExportAnalysisToPostmanDto> exportAnalysisToPostman(UUID analysisId) {
        var exportPostmanDto = new ExportAnalysisToPostmanDto();

        return new ResponseEntity<>(exportPostmanDto, HttpStatus.OK);
    }
}
