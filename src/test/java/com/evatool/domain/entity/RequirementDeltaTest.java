package com.evatool.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class RequirementDeltaTest extends SuperEntityTest {

    @Test
    void setSetOverwriteMerit_SetNull_Throws(){
        // given
        var delta = getPersistedRequirementDelta();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> delta.setOverwriteMerit(null));
    }

    @Test
    void setSetOverwriteMerit_SetInvalidCombination_Throws(){
        // given
        var delta = getPersistedRequirementDelta();

        // when
        delta.getImpact().setMerit(.5f);

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> delta.setOverwriteMerit(.8f));
    }

    @Test
    void setSetOverwriteMerit_ExceedsBounds_Throw() {
        // given
        var delta = getPersistedRequirementDelta();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> delta.setOverwriteMerit(-3f));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> delta.setOverwriteMerit(3f));
    }

    @ParameterizedTest
    @CsvSource({ // merit, overwriteMerit, red, green, blue
            "0, 0, 102, 102, 102",
            "-1, 0, 255, 255, 0",
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
        assertThat(color).isEqualTo(new Color(r, g, b));
    }
}
