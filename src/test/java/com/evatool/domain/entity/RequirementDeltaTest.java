package com.evatool.domain.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
class RequirementDeltaTest extends SuperEntityTest {

    @Test
    void testConstructor_PassNull_Throws() {
        // given
        var impact = getPersistedImpact();
        var requirement = getPersistedRequirement();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new RequirementDelta(null, impact, requirement));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new RequirementDelta(0f, null, requirement));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new RequirementDelta(0f, impact, null));
    }

    @Test
    void testSetDelta_ExceedsBounds_Throw() {
        // given
        var entity = getPersistedRequirementDelta();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> entity.setOverwriteMerit(-3f));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> entity.setOverwriteMerit(3f));
    }
}
