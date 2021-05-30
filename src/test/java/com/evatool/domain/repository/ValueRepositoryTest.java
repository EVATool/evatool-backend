package com.evatool.domain.repository;

import com.evatool.domain.entity.Value;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValueRepositoryTest extends CrudRepositoryTest<Value> implements FindByAnalysisRepositoryTest {

    @Test
    void testCascadeDelete() {
        // given
        var analysis = getPersistedAnalysis();
        var stakeholder = getPersistedStakeholder(analysis);
        var value = getPersistedValue(analysis);
        var impact = getPersistedImpact(analysis, value, stakeholder);

        // when
        valueRepository.delete(value);

        // then
        assertThat(impactRepository.findAll()).isEmpty();
    }
}
