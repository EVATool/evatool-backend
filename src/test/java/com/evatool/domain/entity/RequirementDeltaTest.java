package com.evatool.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
class RequirementDeltaTest extends SuperEntityTest {

    @Test
    void testSetDelta_ExceedsBounds_Throw() {
        // given
        var delta = getPersistedRequirementDelta();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> delta.setOverwriteMerit(-3f));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> delta.setOverwriteMerit(3f));
    }

    @ParameterizedTest
    @CsvSource({"1, 1, 0, 255, 0"})
    void testGetMeritColor(Float merit, Float overwriteMerit, int r, int g, int b) {
        // given
        var delta = getPersistedRequirementDelta();
        delta.getImpact().setMerit(merit);

        // when
        delta.setOverwriteMerit(overwriteMerit);

        // then
        var color = delta.getMeritColor();
        assertThat(color).isEqualTo(new Color(r, g, b));
    }
}
