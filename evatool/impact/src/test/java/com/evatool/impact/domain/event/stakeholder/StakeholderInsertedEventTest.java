package com.evatool.impact.domain.event.stakeholder;

import com.evatool.impact.common.TestSettings;
import com.evatool.impact.domain.repository.ImpactStakeholderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.evatool.impact.common.TestDataGenerator.getStakeholder;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StakeholderInsertedEventTest {
    @Autowired
    private ImpactStakeholderRepository stakeholderRepository;

    @Autowired
    private StakeholderInsertedEventPublisher stakeholderInsertedEventPublisher;

    @Autowired
    private StakeholderInsertedEventListener stakeholderInsertedEventListener;

    @Test
    public void testOnApplicationEvent_PublishEvent_StakeholderIdIsNotNull() throws InterruptedException {
        // given
        var stakeholder = getStakeholder();

        // when
        stakeholderInsertedEventPublisher.onStakeholderInserted(stakeholder);
        Thread.sleep(TestSettings.WAIT_MILLIS_FOR_ASYNC_EVENT);

        // then
        assertThat(stakeholder.getId()).isNotNull();
    }

    @Test
    public void testOnApplicationEvent_PublishEvent_ReturnInsertedStakeholder() throws InterruptedException {
        // given
        var stakeholder = getStakeholder();

        // when
        stakeholderInsertedEventPublisher.onStakeholderInserted(stakeholder);
        Thread.sleep(TestSettings.WAIT_MILLIS_FOR_ASYNC_EVENT);
        var found = stakeholderRepository.findById(stakeholder.getId()).orElse(null);

        // then
        assertThat(found).isNotNull();
    }
}
