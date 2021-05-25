package com.evatool.domain.entity;

import com.evatool.common.enums.StakeholderLevel;
import com.evatool.common.enums.StakeholderPriority;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
class StakeholderTest extends SuperEntityTest {

    @Test
    void testConstructor_PassNull_Throws() {
        // given
        var analysis = getPersistedAnalysis();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Stakeholder(null, StakeholderPriority.ONE, StakeholderLevel.INDIVIDUAL, analysis));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Stakeholder("", null, StakeholderLevel.INDIVIDUAL, analysis));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Stakeholder("", StakeholderPriority.ONE, null, analysis));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Stakeholder("", StakeholderPriority.ONE, StakeholderLevel.INDIVIDUAL, null));
    }
}
