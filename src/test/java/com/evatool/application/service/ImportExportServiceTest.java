package com.evatool.application.service;

import com.evatool.application.service.impl.ImportExportServiceImpl;
import com.evatool.domain.entity.Analysis;
import com.evatool.domain.repository.AnalysisRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ImportExportServiceTest {

    @Autowired
    private ImportExportServiceImpl importExportService;

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
        var analysis = new Analysis("", "", false);

        // when
        var analysisJson = importExportService.exportAnalyses();

        // then
        System.out.println(analysisJson);


    }
}
