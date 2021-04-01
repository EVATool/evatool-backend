package com.evatool.impact.domain.event;

import com.evatool.global.event.value.ValueCreatedEvent;
import com.evatool.global.event.value.ValueDeletedEvent;
import com.evatool.global.event.value.ValueUpdatedEvent;
import com.evatool.impact.common.exception.EventEntityAlreadyExistsException;
import com.evatool.impact.common.exception.EventEntityDoesNotExistException;
import com.evatool.impact.domain.event.json.mapper.ImpactValueJsonMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ImpactValueEventListenerTest extends EventListenerTest {

    @Autowired
    private ImpactValueEventListener impactValueEventListener;

    @Nested
    class Created {

        @Test
        void testOnValueCreatedEvent_PublishEvent_ValueCreated() {
            // given
            var value = saveDummyValueChildren();
            var json = ImpactValueJsonMapper.toJson(value);

            // when
            var valueCreatedEvent = new ValueCreatedEvent(this, json);
            impactValueEventListener.onValueCreatedEvent(valueCreatedEvent);

            // then
            var createdByEvent = valueRepository.findById(value.getId());
            assertThat(createdByEvent).contains(value);
        }

        @Test
        void testOnValueCreatedEvent_ValueAlreadyExists_ThrowEventEntityAlreadyExistsException() {
            // given
            var value = saveDummyValueChildren();
            var json = ImpactValueJsonMapper.toJson(value);
            valueRepository.save(value);

            // when
            var valueCreatedEvent = new ValueCreatedEvent(this, json);

            // then
            assertThatExceptionOfType(EventEntityAlreadyExistsException.class).isThrownBy(() -> impactValueEventListener.onValueCreatedEvent(valueCreatedEvent));
        }
    }

    @Nested
    class Deleted {

        @Test
        void testOnValueDeletedEvent_PublishEvent_ValueDeleted() {
            // given
            var value = saveDummyValueChildren();
            var json = ImpactValueJsonMapper.toJson(value);
            valueRepository.save(value);

            // when
            var valueDeletedEvent = new ValueDeletedEvent(this, json);
            impactValueEventListener.onValueDeletedEvent(valueDeletedEvent);

            // then
            var deletedByEventValue = valueRepository.findById(value.getId());
            assertThat(deletedByEventValue).isNotPresent();
        }

        @Test
        void testOnValueDeletedEvent_ValueDoesNotExist_ThrowEventEntityDoesNotExistException() {
            // given
            var value = saveDummyValueChildren();
            var json = ImpactValueJsonMapper.toJson(value);

            // when
            var valueDeletedEvent = new ValueDeletedEvent(this, json);

            // then
            assertThatExceptionOfType(EventEntityDoesNotExistException.class).isThrownBy(() -> impactValueEventListener.onValueDeletedEvent(valueDeletedEvent));
        }
    }

    @Nested
    class Updated {

        @Test
        void testOnValueUpdatedEvent_PublishEvent_ValueUpdated() {
            // given
            var value = saveDummyValueChildren();
            var json = ImpactValueJsonMapper.toJson(value);
            valueRepository.save(value);

            // when
            var valueUpdatedEvent = new ValueUpdatedEvent(this, json);
            impactValueEventListener.onValueUpdatedEvent(valueUpdatedEvent);

            // then
            var updatedByEventValue = valueRepository.findById(value.getId());
            assertThat(updatedByEventValue).contains(value);
        }

        @Test
        void testOnValueUpdatedEvent_ValueDoesNotExists_ThrowEventEntityDoesNotExistException() {
            // given
            var value = saveDummyValueChildren();
            var json = ImpactValueJsonMapper.toJson(value);

            // when
            var valueUpdatedEvent = new ValueUpdatedEvent(this, json);

            // then
            assertThatExceptionOfType(EventEntityDoesNotExistException.class).isThrownBy(() -> impactValueEventListener.onValueUpdatedEvent(valueUpdatedEvent));
        }
    }
}
