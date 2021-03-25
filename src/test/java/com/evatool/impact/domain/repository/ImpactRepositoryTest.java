package com.evatool.impact.domain.repository;

import com.evatool.impact.domain.entity.Dimension;
import com.evatool.impact.domain.entity.Impact;
import com.evatool.impact.domain.entity.ImpactAnalysis;
import com.evatool.impact.domain.entity.ImpactStakeholder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

import static com.evatool.impact.common.TestDataGenerator.*;
import static org.assertj.core.api.Assertions.assertThat;

class ImpactRepositoryTest extends RepositoryTest {

    @Test
    void testFindAllByAnalysisId_AnalysisWithTwoImpacts_ReturnImpactsByAnalysisId() {
        // given
        var analysis = saveDummyAnalysis();
        var impact1 = saveFullDummyImpact(analysis);
        var impact2 = saveFullDummyImpact(analysis);

        // when

        // then
        var impactsOfAnalysis = impactRepository.findAllByAnalysisId(impact1.getAnalysis().getId());
        assertThat(impactsOfAnalysis).isEqualTo(Arrays.asList(impact1, impact2));
    }
}