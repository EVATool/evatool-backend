package com.evatool.application.controller.impl;

import com.evatool.application.controller.api.ImportExportController;
import com.evatool.application.service.impl.ImportExportServiceImpl;
import com.evatool.common.util.AuthUtil;
import com.evatool.common.util.UriUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

// TODO how do deal with database migration changes? (Done in Json Mappers?)
@RestController
public class ImportExportControllerImpl implements ImportExportController {

    @Autowired
    private ImportExportServiceImpl importExportService;

    @Override
    @PostMapping(UriUtil.IMPORT_ANALYSES)
    @PreAuthorize(AuthUtil.BY_WRITER)
    public ResponseEntity<Void> importAnalyses(String importAnalyses) {
        importExportService.importAnalyses(importAnalyses);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @GetMapping(UriUtil.EXPORT_ANALYSES)
    @PreAuthorize(AuthUtil.BY_READER)
    // TODO optional parameter: filename
    public ResponseEntity<byte[]> exportAnalyses(Iterable<UUID> analysisIdList) { // TODO test if validation of DTOs still work
        var exportJsonString = importExportService.exportAnalyses(analysisIdList);
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
