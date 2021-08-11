package com.evatool.application.controller.impl;

import com.evatool.application.controller.api.ImportExportController;
import com.evatool.application.service.impl.ImportExportServiceImpl;
import com.evatool.common.util.AuthUtil;
import com.evatool.common.util.UriUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.UUID;

@Api(tags = "Import-Export API-Endpoint")
@RestController
@CrossOrigin
public class ImportExportControllerImpl implements ImportExportController {

    @Autowired
    private ImportExportServiceImpl importExportService;

    @Override
    @PostMapping(UriUtil.IMPORT_ANALYSES)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<Void> importAnalyses(String importAnalyses) { // TODO how to take in file?
        importExportService.importAnalyses(importAnalyses);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @GetMapping(UriUtil.EXPORT_ANALYSES)
    @PreAuthorize(AuthUtil.BY_READER)
    // TODO optional parameter: filename
    // TODO test if actual file is being downloaded in browser when called from frontend
    public ResponseEntity<byte[]> exportAnalyses(UUID[] analysisIds) { // TODO test if validation of DTOs still work
        var exportJsonString = importExportService.exportAnalyses(Arrays.asList(analysisIds));
        var exportJsonBytes = exportJsonString.getBytes();
        var filename = "Analysis_export.json";

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(exportJsonBytes.length)
                .body(exportJsonBytes);
    }
}
