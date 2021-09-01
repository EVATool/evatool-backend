package com.evatool.domain.entity;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class StakeholderTest extends SuperEntityTest {

    @Test
    void testGetImpacted_NoImpacts_ReturnNull() {
        // given
        var stakeholder = getPersistedStakeholder();

        // when
        var impacted = stakeholder.getImpacted();

        // then
        assertThat(impacted).isNull();
    }

    @ParameterizedTest
    @CsvSource({"-1, 1, 0", "0, 1, 0.5", "0, -1, -0.5", "0, 0, 0", "1, 1, 1", "-1, -1, -1", "0.8, 0.2, 0.5"})
    void testGetImpacted_ExistingImpacts_ReturnImpacted(Float impact1Merit, Float impact2Merit, Float impactedShould) {
        // given
        var analysis = getPersistedAnalysis();
        var stakeholder = getPersistedStakeholder(analysis);

        // when
        var impact1 = getPersistedImpact(analysis);
        impact1.setMerit(impact1Merit);
        stakeholder.getImpacts().add(impact1);

        var impact2 = getPersistedImpact(analysis);
        impact2.setMerit(impact2Merit);
        stakeholder.getImpacts().add(impact2);

        // then
        var impacted = stakeholder.getImpacted();
        assertThat(impacted).isEqualTo(impactedShould);
    }
}
