package com.evatool.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
class ImpactTest extends SuperEntityTest {

    @Test
    void testSetMerit_ExceedsBounds_Throws() {
        // given
        var entity = getPersistedImpact();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> entity.setMerit(-1.1f));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> entity.setMerit(1.1f));
    }

    @ParameterizedTest
    @ValueSource(floats = {0f, .5f, 1f})
    void testIsGoal(float value) {
        // given
        var entity = getPersistedImpact();

        // when
        entity.setMerit(value);

        // then
        assertThat(entity.getIsGoal()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(floats = {-1f, -.5f, -0.0001f})
    void testIsRisk(float value) {
        // given
        var entity = getPersistedImpact();

        // when
        entity.setMerit(value);

        // then
        assertThat(entity.getIsRisk()).isTrue();
    }

    @ParameterizedTest
    @ValueSource(floats = {-1f, -.5f, 0f, .5f, 1f})
    void testIsRiskIsGoal_MutuallyExclusive(float value) {
        // given
        var entity = getPersistedImpact();

        // when
        entity.setMerit(value);

        // then
        assertThat(entity.getIsRisk()).isNotEqualTo(entity.getIsGoal());
    }
}
