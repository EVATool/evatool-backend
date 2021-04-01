package com.evatool.analysis.model.entity;

import org.junit.jupiter.api.Test;

import static com.evatool.analysis.common.TestDataGenerator.getStakeholder;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class StakeholderTest {

    @Test
    public void testSetStakeholderName() {

        // given
        var stakeholder = getStakeholder();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> stakeholder.setStakeholderName(null));
    }

    @Test
    public void testSetStakeholderLevel() {

        // given
        var stakeholder = getStakeholder();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> stakeholder.setStakeholderLevel(null));
    }

    @Test
    public void testSetStakeholderPriority() {

        // given
        var stakeholder = getStakeholder();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> stakeholder.setPriority(null));
    }

    @Test
    public void testSetStakeholderGuiId() {

        // given
        var stakeholder = getStakeholder();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> stakeholder.setGuiId(null));
    }

}
