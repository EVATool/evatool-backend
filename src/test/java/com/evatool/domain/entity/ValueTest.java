package com.evatool.domain.entity;

import com.evatool.common.enums.ValueType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
class ValueTest extends SuperEntityTest {

    @Test
    void testConstructor_PassNull_Throws() {
        // given
        var analysis = getPersistedAnalysis();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Value(null, "", ValueType.SOCIAL, false, analysis));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Value("", null, ValueType.SOCIAL, false, analysis));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Value("", "", null, false, analysis));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Value("", "", ValueType.SOCIAL, null, analysis));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Value("", "", ValueType.SOCIAL, false, null));
    }
}
