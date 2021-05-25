package com.evatool.domain.repository;

import com.evatool.domain.entity.Stakeholder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

public class StakeholderRepositoryTest extends CrudRepositoryTest<Stakeholder> implements FindByAnalysisRepositoryTest {

    @Test
    void testCascadeDelete() {
        // given
        var analysis = getPersistedAnalysis();
        var stakeholder = getPersistedStakeholder(analysis);
        var value = getPersistedValue(analysis);
        var impact = getPersistedImpact(analysis, value, stakeholder);

        // when
        stakeholderRepository.delete(stakeholder);

        // then
        assertThat(impactRepository.findAll()).isEmpty();
    }
}
