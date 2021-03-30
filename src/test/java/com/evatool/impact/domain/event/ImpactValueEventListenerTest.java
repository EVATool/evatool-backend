package com.evatool.impact.domain.event;

import com.evatool.global.event.value.ValueCreatedEvent;
import com.evatool.global.event.value.ValueDeletedEvent;
import com.evatool.global.event.value.ValueUpdatedEvent;
import com.evatool.impact.common.exception.EventEntityAlreadyExistsException;
import com.evatool.impact.common.exception.EventEntityDoesNotExistException;
import com.evatool.impact.domain.event.json.mapper.ImpactValueJsonMapper;
import com.evatool.impact.domain.repository.ImpactValueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.evatool.impact.common.TestDataGenerator.createDummyValue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class ImpactValueEventListenerTest {

    @Autowired
    private ImpactValueRepository impactValueRepository;

    @Autowired
    private ImpactValueEventListener impactValueEventListener;

    @BeforeEach
    void clearData() {
        impactValueRepository.deleteAll();
    }

    @Nested
    class Created {

        @Test
        void testOnStakeholderCreatedEvent_PublishEvent_StakeholderCreated() {
            // given
            var value = createDummyValue();
            var json = ImpactValueJsonMapper.toJson(value);

            // when
            var valueCreatedEvent = new ValueCreatedEvent(this, json);
            impactValueEventListener.onValueCreatedEvent(valueCreatedEvent);

            // then
            var createdByEvent = impactValueRepository.findById(value.getId());
            assertThat(createdByEvent).contains(value);
        }

        @Test
        void testOnStakeholderCreatedEvent_StakeholderAlreadyExists_ThrowEventEntityAlreadyExistsException() {
            // given
            var value = createDummyValue();
            var json = ImpactValueJsonMapper.toJson(value);
            impactValueRepository.save(value);

            // when
            var valueCreatedEvent = new ValueCreatedEvent(this, json);

            // then
            assertThatExceptionOfType(EventEntityAlreadyExistsException.class).isThrownBy(() -> impactValueEventListener.onValueCreatedEvent(valueCreatedEvent));
        }
    }

    @Nested
    class Deleted {

        @Test
        void testOnStakeholderDeletedEvent_PublishEvent_StakeholderDeleted() {
            // given
            var value = createDummyValue();
            var json = ImpactValueJsonMapper.toJson(value);
            impactValueRepository.save(value);

            // when
            var valueDeletedEvent = new ValueDeletedEvent(this, json);
            impactValueEventListener.onValueDeletedEvent(valueDeletedEvent);

            // then
            var deletedByEventStakeholder = impactValueRepository.findById(value.getId());
            assertThat(deletedByEventStakeholder).isNotPresent();
        }

        @Test
        void testOnStakeholderDeletedEvent_StakeholderDoesNotExist_ThrowEventEntityDoesNotExistException() {
            // given
            var value = createDummyValue();
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
        void testOnStakeholderUpdatedEvent_PublishEvent_StakeholderUpdated() {
            // given
            var value = createDummyValue();
            var json = ImpactValueJsonMapper.toJson(value);
            impactValueRepository.save(value);

            // when
            var valueUpdatedEvent = new ValueUpdatedEvent(this, json);
            impactValueEventListener.onValueUpdatedEvent(valueUpdatedEvent);

            // then
            var updatedByEventStakeholder = impactValueRepository.findById(value.getId());
            assertThat(updatedByEventStakeholder).contains(value);
        }

        @Test
        void testOnStakeholderUpdatedEvent_StakeholderDoesNotExists_ThrowEventEntityDoesNotExistException() {
            // given
            var value = createDummyValue();
            var json = ImpactValueJsonMapper.toJson(value);

            // when
            var valueUpdatedEvent = new ValueUpdatedEvent(this, json);

            // then
            assertThatExceptionOfType(EventEntityDoesNotExistException.class).isThrownBy(() -> impactValueEventListener.onValueUpdatedEvent(valueUpdatedEvent));
        }
    }
}
