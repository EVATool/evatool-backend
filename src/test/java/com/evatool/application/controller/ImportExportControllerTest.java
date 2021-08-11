package com.evatool.application.controller;

import com.evatool.common.util.PrintUtil;
import com.evatool.common.util.UriUtil;
import com.evatool.domain.entity.Analysis;
import com.evatool.domain.repository.AnalysisRepository;
import lombok.Getter;
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
    @Getter
    public TestRestTemplate rest;

    @Autowired
    private AnalysisRepository analysisRepository;

    @BeforeEach
    protected void clearDatabase() {
        analysisRepository.deleteAll();
    }

    @Test
    void testImportAnalyses() {
        // given

        // when

        // then

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
        PrintUtil.prettyPrintJson(jsonContent);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(jsonContent).isNotNull();
        assertThat(jsonContent.getClass()).isEqualTo(String.class);
        assertThat(jsonContent).isNotEmpty();
    }
}
