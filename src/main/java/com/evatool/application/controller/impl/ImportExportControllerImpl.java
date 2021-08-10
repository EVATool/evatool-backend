package com.evatool.application.controller.impl;

import com.evatool.application.controller.api.ImportExportController;
import com.evatool.common.util.AuthUtil;
import com.evatool.common.util.UriUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO how do deal with database migration changes?
@RestController
public class ImportExportControllerImpl implements ImportExportController {

    @Override
    @GetMapping(UriUtil.EXPORT_ANALYSES)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<byte[]> exportAnalyses() {
        var jsonContent = "content test test".getBytes();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=customers.json")
                .contentType(MediaType.APPLICATION_JSON)
                .contentLength(jsonContent.length)
                .body(jsonContent);
    }
}
