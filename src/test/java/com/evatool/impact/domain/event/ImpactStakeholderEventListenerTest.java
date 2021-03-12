package com.evatool.impact.domain.event;

import com.evatool.global.event.stakeholder.StakeholderCreatedEvent;
import com.evatool.global.event.stakeholder.StakeholderDeletedEvent;
import com.evatool.global.event.stakeholder.StakeholderUpdatedEvent;
import com.evatool.impact.common.exception.EventEntityAlreadyExistsException;
import com.evatool.impact.common.exception.EventEntityDoesNotExistException;
import com.evatool.impact.domain.entity.ImpactStakeholder;
import com.evatool.impact.domain.repository.ImpactStakeholderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@ActiveProfiles(profiles = "non-async")
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
            var id = UUID.randomUUID();
            var name = "name";
            var json = String.format("{\"id\":\"%s\",\"name\":\"%s\"}", id.toString(), name);

            // when
            var stakeholderCreatedEvent = new StakeholderCreatedEvent(this, json);
            impactStakeholderEventListener.onStakeholderCreatedEvent(stakeholderCreatedEvent);

            // then
            var createdByEvent = stakeholderRepository.findById(id);
            assertThat(createdByEvent).isPresent();
            assertThat(createdByEvent.get().getId()).isEqualTo(id);
            assertThat(createdByEvent.get().getName()).isEqualTo(name);
        }

        @Test
        void testOnStakeholderCreatedEvent_StakeholderAlreadyExists_ThrowEventEntityAlreadyExistsException() {
            // given
            var id = UUID.randomUUID();
            var name = "name";
            var json = String.format("{\"id\":\"%s\",\"name\":\"%s\"}", id.toString(), name);

            var stakeholder = new ImpactStakeholder(id, name);
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
            var id = UUID.randomUUID();
            var name = "name";
            var json = String.format("{\"id\":\"%s\",\"name\":\"%s\"}", id.toString(), name);

            var stakeholder = new ImpactStakeholder(id, name);
            stakeholderRepository.save(stakeholder);

            // when
            var stakeholderDeletedEvent = new StakeholderDeletedEvent(this, json);
            impactStakeholderEventListener.onStakeholderDeletedEvent(stakeholderDeletedEvent);

            // then
            var deletedByEventStakeholder = stakeholderRepository.findById(id);
            assertThat(deletedByEventStakeholder).isNotPresent();
        }

        @Test
        void testOnStakeholderDeletedEvent_StakeholderDoesNotExist_ThrowEventEntityDoesNotExistException() {
            // given
            var id = UUID.randomUUID();
            var name = "name";
            var json = String.format("{\"id\":\"%s\",\"name\":\"%s\"}", id.toString(), name);

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
            var id = UUID.randomUUID();
            var name = "name";
            var json = String.format("{\"id\":\"%s\",\"name\":\"%s\"}", id.toString(), name);

            var stakeholder = new ImpactStakeholder(id, "old_name");
            stakeholderRepository.save(stakeholder);

            // when
            var stakeholderUpdatedEvent = new StakeholderUpdatedEvent(this, json);
            impactStakeholderEventListener.onStakeholderUpdatedEvent(stakeholderUpdatedEvent);

            // then
            var updatedByEventStakeholder = stakeholderRepository.findById(id);
            assertThat(updatedByEventStakeholder).isPresent();
            assertThat(updatedByEventStakeholder.get().getId()).isEqualTo(id);
            assertThat(updatedByEventStakeholder.get().getName()).isEqualTo(name);
        }

        @Test
        void testOnStakeholderUpdatedEvent_StakeholderDoesNotExists_ThrowEventEntityDoesNotExistException() {
            // given
            var id = UUID.randomUUID();
            var name = "name";
            var json = String.format("{\"id\":\"%s\",\"name\":\"%s\"}", id.toString(), name);

            // when
            var stakeholderUpdatedEvent = new StakeholderUpdatedEvent(this, json);

            // then
            assertThatExceptionOfType(EventEntityDoesNotExistException.class).isThrownBy(() -> impactStakeholderEventListener.onStakeholderUpdatedEvent(stakeholderUpdatedEvent));
        }
    }
}
