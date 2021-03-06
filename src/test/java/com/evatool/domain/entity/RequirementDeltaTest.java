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

    // TODO figure out exactly what colors to display and THEN write tests
    @ParameterizedTest
    @CsvSource({ // merit, overwriteMerit, red, green, blue
            //"0, 0, 120, 120, 120",
            //"1, 0, 170, 170, 170",
            //"1, 1, 220, 220, 220",
            "-1, -1, 255, 0, 0",
            "1, 1, 0, 255, 0",
    })
    void testGetMeritColor(Float merit, Float overwriteMerit, int r, int g, int b) {
        // given
        var delta = getPersistedRequirementDelta();
        delta.getImpact().setMerit(merit);

        // when
        delta.setOverwriteMerit(overwriteMerit);

        // then
        var color = delta.getMeritColor();
        System.out.println(String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
        assertThat(color).isEqualTo(new Color(r, g, b));
    }
}
