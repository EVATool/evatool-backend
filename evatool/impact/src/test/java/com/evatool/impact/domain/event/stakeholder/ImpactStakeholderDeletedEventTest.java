package com.evatool.impact.domain.event.stakeholder;

import com.evatool.impact.common.TestSettings;
import com.evatool.impact.domain.repository.ImpactStakeholderRepository;
import org.awaitility.core.ConditionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;

import static com.evatool.impact.common.TestDataGenerator.getStakeholder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@ActiveProfiles(profiles = "non-async")
public class ImpactStakeholderDeletedEventTest {
    public static final ConditionFactory WAIT = await()
            .atMost(Duration.ofMillis(TestSettings.WAIT_MILLIS_FOR_ASYNC_EVENT))
            .pollInterval(Duration.ofMillis(TestSettings.WAIT_MILLIS_FOR_ASYNC_EVENT_POLL));

    @Autowired
    private ImpactStakeholderRepository stakeholderRepository;

    @Autowired
    private StakeholderDeletedEventPublisher stakeholderDeletedEventPublisher;

    @Autowired
    private StakeholderDeletedEventListener stakeholderDeletedEventListener;

    @Test
    public void testOnApplicationEvent_PublishEvent_ReturnNull() throws InterruptedException {
        // given
        var stakeholder = getStakeholder();
        stakeholderRepository.save(stakeholder);

        // when
        stakeholderDeletedEventPublisher.onStakeholderDeleted(stakeholder);

        // then
        assertThat(stakeholderRepository.findById(stakeholder.getId()).isEmpty());
    }
}
