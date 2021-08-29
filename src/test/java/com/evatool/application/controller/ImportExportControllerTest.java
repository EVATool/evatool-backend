package com.evatool.application.controller;

import com.evatool.application.dto.AnalysisDto;
import com.evatool.common.util.IterableUtil;
import com.evatool.common.util.PrintUtil;
import com.evatool.common.util.UriUtil;
import com.evatool.domain.entity.Analysis;
import com.evatool.domain.repository.AnalysisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ImportExportControllerTest {

    @Autowired
    private TestRestTemplate rest;

    @Autowired
    private AnalysisRepository analysisRepository;

    @BeforeEach
    protected void clearDatabase() {
        analysisRepository.deleteAll();
    }

    @Test
    void testImportAnalyses() {
        // given
        var analysis1 = analysisRepository.save(new Analysis("", "", false));
        var analysis2 = analysisRepository.save(new Analysis("", "", false));

        // when
        var exportResponse = rest.getForEntity(UriUtil.EXPORT_ANALYSES + "?analysisIds=" + analysis1.getId() + "," + analysis2.getId(), String.class);
        var importResponse = rest.postForEntity(UriUtil.IMPORT_ANALYSES, exportResponse.getBody(), AnalysisDto[].class);
        var importedAnalyses = importResponse.getBody();

        // then
        assertThat(importResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(importedAnalyses).hasSize(2);
        var analyses = analysisRepository.findAll();
        assertThat(IterableUtil.iterableSize(analyses)).isEqualTo(4);
    }

    @Test
    void testExportAnalyses() {
        // given
        var analysis1 = analysisRepository.save(new Analysis("", "", false));
        var analysis2 = analysisRepository.save(new Analysis("", "", false));

        // when
        var response = rest.getForEntity(UriUtil.EXPORT_ANALYSES + "?analysisIds=" + analysis1.getId() + "," + analysis2.getId(), String.class);
        var jsonContent = response.getBody();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(jsonContent).isNotNull();
        assertThat(jsonContent.getClass()).isEqualTo(String.class);
        assertThat(jsonContent).isNotEmpty();
    }

    @Test
    void testExportAnalyses_FilenameProvided() {
        // given
        var analysis1 = analysisRepository.save(new Analysis("", "", false));
        var analysis2 = analysisRepository.save(new Analysis("", "", false));

        // when
        var response = rest.getForEntity(UriUtil.EXPORT_ANALYSES + "?analysisIds=" + analysis1.getId() + "," + analysis2.getId() + "&filename=abc", String.class);
        var jsonContent = response.getBody();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentDisposition().getFilename()).isEqualTo("abc.json");
    }

    @Test
    void testExportAnalyses_FilenameNotProvided() {
        // given
        var analysis1 = analysisRepository.save(new Analysis("", "", false));
        var analysis2 = analysisRepository.save(new Analysis("", "", false));

        // when
        var response = rest.getForEntity(UriUtil.EXPORT_ANALYSES + "?analysisIds=" + analysis1.getId() + "," + analysis2.getId(), String.class);
        var jsonContent = response.getBody();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getHeaders().getContentDisposition().getFilename()).isEqualTo("Analysis-Export.json");
    }
}
