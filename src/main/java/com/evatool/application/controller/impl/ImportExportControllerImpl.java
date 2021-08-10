package com.evatool.application.controller.impl;

import com.evatool.application.controller.api.ImportExportController;
import com.evatool.common.util.AuthUtil;
import com.evatool.common.util.UriUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

// TODO how do deal with database migration changes?
public class ImportExportControllerImpl implements ImportExportController {

    @Override
    @GetMapping(UriUtil.EXPORT_ANALYSES)
    @PreAuthorize(AuthUtil.BY_READER)
    public ResponseEntity<byte[]> exportAnalyses() {
        var jsonContent = "content test test".getBytes();
        String fileName = "test.json";

        var respHeaders = new HttpHeaders();
        respHeaders.setContentLength(jsonContent.length);
        respHeaders.setContentType(new MediaType("text", "json"));
        respHeaders.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        respHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);

        return new ResponseEntity(jsonContent, respHeaders, HttpStatus.OK);
    }
}
