package com.evatool.impact.persistence.event.stakeholder;

import com.evatool.impact.persistence.TestDataGenerator;
import com.evatool.impact.persistence.TestSettings;
import com.evatool.impact.persistence.event.TestEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class StakeholderDeletedEventMockTest {
    @Autowired
    private StakeholderDeletedEventPublisher stakeholderDeletedEventPublisher;

    @MockBean
    private StakeholderDeletedEventListener stakeholderDeletedEventListener;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    public void testOnApplicationEvent_PublishEvent_ReceivePublishedEvent() throws InterruptedException {
        // given
        var stakeholder = TestDataGenerator.getStakeholder();

        // when
        stakeholderDeletedEventPublisher.onStakeholderDeleted(stakeholder);
        Thread.sleep(TestSettings.WAIT_MILLIS_FOR_ASYNC_EVENT);

        // then
        verify(stakeholderDeletedEventListener, times(1)).onApplicationEvent(any(StakeholderDeletedEvent.class));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    public void testOnApplicationEvent_PublishEvent_ReceivePublishedEventNTimes(int value) throws InterruptedException {
        for (int i = 0; i < value; i++) {
            // given
            var stakeholder = TestDataGenerator.getStakeholder();

            // when
            stakeholderDeletedEventPublisher.onStakeholderDeleted(stakeholder);
        }
        Thread.sleep(TestSettings.WAIT_MILLIS_FOR_ASYNC_EVENT);

        // then
        verify(stakeholderDeletedEventListener, times(value)).onApplicationEvent(any(StakeholderDeletedEvent.class));
    }

    @Test
    public void testOnApplicationEvent_PublishWrongEvent_DoNotReceivePublishedEvent() throws InterruptedException {
        // given
        var stakeholder = TestDataGenerator.getStakeholder();

        // when
        applicationEventPublisher.publishEvent(new TestEvent(this));
        Thread.sleep(TestSettings.WAIT_MILLIS_FOR_ASYNC_EVENT);

        // then
        verify(stakeholderDeletedEventListener, times(0)).onApplicationEvent(any(StakeholderDeletedEvent.class));
    }
}