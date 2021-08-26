package com.evatool.domain.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
class SuperEntityImplTest extends SuperEntityTest {

    @Test
    void testSetId_SetId_OK() {
        // given
        var superEntity = new SuperEntityImpl();

        // when
        superEntity.setId(UUID.randomUUID());

        // then
        assertThat(superEntity.getId()).isNotNull();
    }

    @Test
    void testSetId_SetExistingId_Throws() {
        // given
        var superEntity = new SuperEntityImpl();

        // when
        superEntity.setId(UUID.randomUUID());

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> superEntity.setId(UUID.randomUUID()));
    }

    @Test
    void testSetId_SetExistingIdToNull_Throws() {
        // given
        var superEntity = new SuperEntityImpl();

        // when
        superEntity.setId(UUID.randomUUID());

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> superEntity.setId(null));
    }

    @Test
    void testSetRealmId_ValidRealm_OK() {
        // given
        var superEntity = new SuperEntityImpl();

        // when
        superEntity.setRealm("evatool-realm");

        // then
        assertThat(superEntity.getRealm()).isNotNull();
    }

    @Test
    void testSetRealmId_InvalidRealm_Throws() {
        // given
        var superEntity = new SuperEntityImpl();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() ->  superEntity.setRealm("%"));
    }

    @Test
    void testSetRealm_SetExistingRealm_Throws() {
        // given
        var superEntity = new SuperEntityImpl();

        // when
        superEntity.setRealm("evatool-realm");

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> superEntity.setRealm("evatool-realm2"));
    }

    static class SuperEntityImpl extends SuperEntity {

    }
}
