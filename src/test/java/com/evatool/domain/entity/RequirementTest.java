package com.evatool.domain.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
class RequirementTest extends SuperEntityTest {

    @Test
    void testConstructor_PassNull_Throws() {
        // given
        var analysis = getPersistedAnalysis();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Requirement(null, analysis));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Requirement("", null));
    }
}
