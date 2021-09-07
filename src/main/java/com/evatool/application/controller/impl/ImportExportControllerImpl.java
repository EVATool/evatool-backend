package com.evatool.application.controller.impl;

import com.evatool.application.controller.api.ImportExportController;
import com.evatool.application.dto.AnalysisDto;
import com.evatool.application.service.impl.ImportExportServiceImpl;
import com.evatool.common.util.AuthUtil;
import com.evatool.common.util.UriUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.UUID;

@Api(tags = "Import-Export API-Endpoint")
@RestController
public class ImportExportControllerImpl implements ImportExportController {

    private static final Logger logger = LoggerFactory.getLogger(ImportExportControllerImpl.class);

    private final ImportExportServiceImpl importExportService;

    public ImportExportControllerImpl(ImportExportServiceImpl importExportService) {
        logger.trace("Constructor");
        this.importExportService = importExportService;
    }

    @Override
    @PostMapping(UriUtil.IMPORT_ANALYSES)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<Iterable<AnalysisDto>> importAnalyses(String importAnalyses) {
        logger.trace("Import Analyses");
        var importedAnalyses = importExportService.importAnalyses(importAnalyses);
        return new ResponseEntity<>(importedAnalyses, HttpStatus.CREATED);
    }

    @Override
    @GetMapping(UriUtil.EXPORT_ANALYSES)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<InputStreamResource> exportAnalyses(UUID[] analysisIds, String filename) {
        logger.trace("Export Analyses");
        var exportJsonString = importExportService.exportAnalyses(Arrays.asList(analysisIds));
        var exportJsonBytes = exportJsonString.getBytes();
        var resource = new InputStreamResource(new ByteArrayInputStream(exportJsonBytes));
        filename += ".json";

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(exportJsonBytes.length)
                .body(resource);
    }
}
