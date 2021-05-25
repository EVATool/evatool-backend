package com.evatool.domain.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
class ImpactTest extends SuperEntityTest {

    @Test
    void  testConstructor_PassNull_Throws() {
        // given
        var analysis = getPersistedAnalysis();
        var value = getPersistedValue(analysis);
        var stakeholder = getPersistedStakeholder(analysis); // ERROR HERE!!!
        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Impact(null, "", value, stakeholder, analysis));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Impact(0.0f, null, value, stakeholder, analysis));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Impact(0.0f, "", null, stakeholder, analysis));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Impact(0.0f, "", value, null, analysis));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Impact(0.0f, "", value, stakeholder, null));
    }

    @Test
    void testSetMerit_ExceedsBounds_Throws() {
        // given
        var entity = getPersistedImpact();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> entity.setMerit(-1.1f));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> entity.setMerit(1.1f));
    }
}
