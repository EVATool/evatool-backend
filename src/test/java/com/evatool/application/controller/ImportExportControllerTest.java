package com.evatool.application.controller;

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
    protected AnalysisRepository analysisRepository;

    @BeforeEach
    protected void clearDatabase() {
        analysisRepository.deleteAll();
    }

    @Test
    void testExportAnalyses() {
        // given
        analysisRepository.save(new Analysis("test Name", "test desc", false));

        // when
        var response = rest.getForEntity(UriUtil.EXPORT_ANALYSES, String.class);
        var jsonContent = response.getBody();

        // then
        System.out.println(response);
        System.out.println(jsonContent);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
}
