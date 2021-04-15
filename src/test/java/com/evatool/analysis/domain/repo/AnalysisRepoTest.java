package com.evatool.analysis.domain.repo;

import com.evatool.analysis.common.TestDataGenerator;
import com.evatool.analysis.domain.model.Analysis;
import com.evatool.analysis.domain.repository.AnalysisRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AnalysisRepoTest {

    @Autowired
    private AnalysisRepository analysisRepository;

    @Test
    void testFindByIdExistingAnalysis() {

        // given
        Analysis analysis = TestDataGenerator.getAnalysis();
        analysis = analysisRepository.save(analysis);

        // when
        Analysis analysisFound = analysisRepository.findById(analysis.getAnalysisId()).orElse(null);

        // then
        assertThat(analysisFound.getAnalysisId()).isEqualTo(analysis.getAnalysisId());
    }

    @Test
    void testSaveInsertedUserIdIsNotNull() {
        // given
        Analysis analysis = TestDataGenerator.getAnalysis();

        // when
        analysis = analysisRepository.save(analysis);

        // then
        assertThat(analysis.getAnalysisId()).isNotNull();
    }

    @Test
    void testSaveInsertedAnalysisIdIsUuid() {
        // given
        Analysis analysis = TestDataGenerator.getAnalysis();

        // when
        analysis = analysisRepository.save(analysis);

        // then
        UUID.fromString(analysis.getAnalysisId().toString());
        assertThat(analysis.getAnalysisId()).isNotNull();

    }

    @Test
    void testDeleteAnalysisReturnNull() {
        // given
        Analysis analysis = TestDataGenerator.getAnalysis();
        analysisRepository.save(analysis);

        // when
        analysisRepository.delete(analysis);
        Analysis analysisFound = analysisRepository.findById(analysis.getAnalysisId()).orElse(null);

        // then
        assertThat(analysisFound).isNull();
    }
}
