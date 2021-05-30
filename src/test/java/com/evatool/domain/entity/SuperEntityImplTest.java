package com.evatool.domain.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
class SuperEntityImplTest extends SuperEntityTest {

    @Test
    void testSetId_ExistingIdSetToNull_Throws() {
        // given
        var superEntity = new SuperEntityImpl();

        // when
        superEntity.setId(UUID.randomUUID());

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> superEntity.setId(null));
    }

    static class SuperEntityImpl extends SuperEntity {

    }
}
