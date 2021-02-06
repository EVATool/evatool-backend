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
public class ImpactStakeholderUpdatedEventTest {
    @Autowired
    private ImpactStakeholderRepository stakeholderRepository;

    @Autowired
    private StakeholderUpdatedEventPublisher stakeholderUpdatedEventPublisher;

    @Autowired
    private StakeholderUpdatedEventListener stakeholderUpdatedEventListener;

    @Test
    public void testOnApplicationEvent_PublishEvent_ReturnUpdatedStakeholder() throws InterruptedException {
        // given
        var stakeholder = getStakeholder();
        stakeholderRepository.save(stakeholder);

        // when
        String newName = "new_name";
        stakeholder.setName(newName);
        stakeholderUpdatedEventPublisher.onStakeholderUpdated(stakeholder);

        // then
        var found = stakeholderRepository.findById(stakeholder.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo(newName);
    }
}
