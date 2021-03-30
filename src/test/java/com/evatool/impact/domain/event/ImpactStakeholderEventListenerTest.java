package com.evatool.impact.domain.event;

import com.evatool.global.event.stakeholder.StakeholderCreatedEvent;
import com.evatool.global.event.stakeholder.StakeholderDeletedEvent;
import com.evatool.global.event.stakeholder.StakeholderUpdatedEvent;
import com.evatool.impact.common.exception.EventEntityAlreadyExistsException;
import com.evatool.impact.common.exception.EventEntityDoesNotExistException;
import com.evatool.impact.domain.event.json.mapper.ImpactStakeholderJsonMapper;
import com.evatool.impact.domain.repository.ImpactStakeholderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.evatool.impact.common.TestDataGenerator.createDummyStakeholder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class ImpactStakeholderEventListenerTest {

    @Autowired
    private ImpactStakeholderRepository stakeholderRepository;

    @Autowired
    private ImpactStakeholderEventListener impactStakeholderEventListener;

    @BeforeEach
    void clearData() {
        stakeholderRepository.deleteAll();
    }

    @Nested
    class Created {

        @Test
        void testOnStakeholderCreatedEvent_PublishEvent_StakeholderCreated() {
            // given
            var stakeholder = createDummyStakeholder();
            var json = ImpactStakeholderJsonMapper.toJson(stakeholder);

            // when
            var stakeholderCreatedEvent = new StakeholderCreatedEvent(this, json);
            impactStakeholderEventListener.onStakeholderCreatedEvent(stakeholderCreatedEvent);

            // then
            var createdByEvent = stakeholderRepository.findById(stakeholder.getId());
            assertThat(createdByEvent).contains(stakeholder);
        }

        @Test
        void testOnStakeholderCreatedEvent_StakeholderAlreadyExists_ThrowEventEntityAlreadyExistsException() {
            // given
            var stakeholder = createDummyStakeholder();
            var json = ImpactStakeholderJsonMapper.toJson(stakeholder);
            stakeholderRepository.save(stakeholder);

            // when
            var stakeholderCreatedEvent = new StakeholderCreatedEvent(this, json);

            // then
            assertThatExceptionOfType(EventEntityAlreadyExistsException.class).isThrownBy(() -> impactStakeholderEventListener.onStakeholderCreatedEvent(stakeholderCreatedEvent));
        }
    }

    @Nested
    class Deleted {

        @Test
        void testOnStakeholderDeletedEvent_PublishEvent_StakeholderDeleted() {
            // given
            var stakeholder = createDummyStakeholder();
            var json = ImpactStakeholderJsonMapper.toJson(stakeholder);
            stakeholderRepository.save(stakeholder);

            // when
            var stakeholderDeletedEvent = new StakeholderDeletedEvent(this, json);
            impactStakeholderEventListener.onStakeholderDeletedEvent(stakeholderDeletedEvent);

            // then
            var deletedByEventStakeholder = stakeholderRepository.findById(stakeholder.getId());
            assertThat(deletedByEventStakeholder).isNotPresent();
        }

        @Test
        void testOnStakeholderDeletedEvent_StakeholderDoesNotExist_ThrowEventEntityDoesNotExistException() {
            // given
            var stakeholder = createDummyStakeholder();
            var json = ImpactStakeholderJsonMapper.toJson(stakeholder);

            // when
            var stakeholderDeletedEvent = new StakeholderDeletedEvent(this, json);

            // then
            assertThatExceptionOfType(EventEntityDoesNotExistException.class).isThrownBy(() -> impactStakeholderEventListener.onStakeholderDeletedEvent(stakeholderDeletedEvent));
        }
    }

    @Nested
    class Updated {

        @Test
        void testOnStakeholderUpdatedEvent_PublishEvent_StakeholderUpdated() {
            // given
            var stakeholder = createDummyStakeholder();
            var json = ImpactStakeholderJsonMapper.toJson(stakeholder);
            stakeholderRepository.save(stakeholder);

            // when
            var stakeholderUpdatedEvent = new StakeholderUpdatedEvent(this, json);
            impactStakeholderEventListener.onStakeholderUpdatedEvent(stakeholderUpdatedEvent);

            // then
            var updatedByEventStakeholder = stakeholderRepository.findById(stakeholder.getId());
            assertThat(updatedByEventStakeholder).contains(stakeholder);
        }

        @Test
        void testOnStakeholderUpdatedEvent_StakeholderDoesNotExists_ThrowEventEntityDoesNotExistException() {
            // given
            var stakeholder = createDummyStakeholder();
            var json = ImpactStakeholderJsonMapper.toJson(stakeholder);

            // when
            var stakeholderUpdatedEvent = new StakeholderUpdatedEvent(this, json);

            // then
            assertThatExceptionOfType(EventEntityDoesNotExistException.class).isThrownBy(() -> impactStakeholderEventListener.onStakeholderUpdatedEvent(stakeholderUpdatedEvent));
        }
    }
}
