package com.evatool.domain.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
}
