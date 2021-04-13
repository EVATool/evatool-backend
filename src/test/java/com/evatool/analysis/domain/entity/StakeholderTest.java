package com.evatool.analysis.domain.entity;

import org.junit.jupiter.api.Test;

import static com.evatool.analysis.common.TestDataGenerator.getStakeholder;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class StakeholderTest {

    @Test
    void testSetStakeholderName() {

        // given
        var stakeholder = getStakeholder();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> stakeholder.setStakeholderName(null));
    }

    @Test
    void testSetStakeholderLevel() {

        // given
        var stakeholder = getStakeholder();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> stakeholder.setStakeholderLevel(null));
    }

    @Test
    void testSetStakeholderPriority() {

        // given
        var stakeholder = getStakeholder();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> stakeholder.setPriority(null));
    }

    @Test
    void testSetStakeholderGuiId() {

        // given
        var stakeholder = getStakeholder();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> stakeholder.setGuiId(null));
    }
}
