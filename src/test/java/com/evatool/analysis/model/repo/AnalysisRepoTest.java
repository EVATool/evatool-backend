package com.evatool.analysis.model.repo;

import com.evatool.analysis.common.TestDataGenerator;
import com.evatool.analysis.model.Analysis;
import com.evatool.analysis.model.Stakeholder;
import com.evatool.analysis.repository.AnalysisRepository;
import com.evatool.analysis.repository.StakeholderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AnalysisRepoTest {

    @Autowired
    private AnalysisRepository analysisRepository;

    @Test
    public void testFindByIdExistingAnalysis() {

        // given
        Analysis analysis = TestDataGenerator.getAnalysis();
        analysisRepository.save(analysis);

        // when
        Analysis analysisFound = analysisRepository.findById(analysis.getAnalysisId()).orElse(null);

        // then
        assertThat(analysisFound.getAnalysisId()).isEqualTo(analysis.getAnalysisId());
    }

    @Test
    public void testSaveInsertedUserIdIsNotNull() {
        // given
        Analysis analysis = TestDataGenerator.getAnalysis();

        // when
        analysisRepository.save(analysis);

        // then
        assertThat(analysis.getAnalysisId()).isNotNull();
    }

    @Test
    public void testSaveInsertedAnalysisIdIsUuid() {
        // given
        Analysis analysis = TestDataGenerator.getAnalysis();

        // when
        analysisRepository.save(analysis);

        // then
        UUID.fromString(analysis.getAnalysisId().toString());
        assertThat(analysis.getAnalysisId()).isNotNull();

    }

    @Test
    public void testDeleteAnalysisReturnNull() {
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
