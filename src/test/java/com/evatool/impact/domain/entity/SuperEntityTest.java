package com.evatool.impact.domain.entity;

import com.evatool.impact.common.exception.InvalidUuidException;
import com.evatool.impact.common.exception.PropertyViolationException;
import org.junit.jupiter.api.Test;

import javax.persistence.Entity;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class SuperEntityTest {

    @Test
    void testCreateEntity_CreatedSuperEntity_IdIsNull() {
        // given
        var superEntity = new SuperEntity();

        // when

        // then
        assertThat(superEntity.getId()).isNull();
    }

    @Test
    void testSetIdUuid_ValidToNullValue_ThrowPropertyViolationException() {
        // given
        var superEntity = new SuperEntity();

        // when
        superEntity.setId(UUID.randomUUID());

        // then
        assertThatExceptionOfType(PropertyViolationException.class).isThrownBy(() -> superEntity.setId((UUID) null));
    }

    @Test
    void testSetIdUuid_IllegalValueSequence_ThrowPropertyViolationException() {
        // given
        var superEntity = new SuperEntity();

        // when
        superEntity.setId(UUID.randomUUID());

        // then
        var id = UUID.randomUUID();
        assertThatExceptionOfType(PropertyViolationException.class).isThrownBy(() -> superEntity.setId(id));
    }

    @Test
    void testSetIdString_ValidToNullValue_ThrowPropertyViolationException() {
        // given
        var superEntity = new SuperEntity();

        // when
        superEntity.setId(UUID.randomUUID());

        // then
        assertThatExceptionOfType(InvalidUuidException.class).isThrownBy(() -> superEntity.setId((String) null));
    }

    @Test
    void testSetIdString_IllegalValueSequence_ThrowPropertyViolationException() {
        // given
        var superEntity = new SuperEntity();

        // when
        superEntity.setId(UUID.randomUUID());

        // then
        var id = UUID.randomUUID().toString();
        assertThatExceptionOfType(PropertyViolationException.class).isThrownBy(() -> superEntity.setId(id));
    }

    @Test
    void testProbeExistingId_InvalidId_ThrowInvalidUuidException() {
        // given
        var id = "invalid id";

        // when

        // then
        assertThatExceptionOfType(InvalidUuidException.class).isThrownBy(() -> SuperEntity.probeExistingId(id));
    }

    @Test
    void testProbeExistingId_NullId_ThrowInvalidUuidException() {
        // given
        var id = (String) null;

        // when

        // then
        assertThatExceptionOfType(InvalidUuidException.class).isThrownBy(() -> SuperEntity.probeExistingId(id));
    }

    @Test
    void testProbeNonExistingId_NotNullId_ThrowInvalidUuidException() {
        // given
        var id = "not null";

        // when

        // then
        assertThatExceptionOfType(PropertyViolationException.class).isThrownBy(() -> SuperEntity.probeNonExistingId(id));
    }

    @Entity
    public class SuperEntityImpl extends SuperEntity {
        public SuperEntityImpl() {
            super();
        }
    }
}