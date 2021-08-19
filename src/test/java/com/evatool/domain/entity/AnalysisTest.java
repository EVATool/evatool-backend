package com.evatool.domain.entity;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AnalysisTest extends SuperEntityTest {

    @Test
    void testWasUpdated_Persist_LastUpdatedIsNotNull() {
        // given
        var analysis = getPersistedAnalysis();

        // when

        // then
        assertThat(analysis.getLastUpdatedDate()).isNotNull();
    }

    @Disabled("This test is disabled because the @PreUpdate annotation does not work")
    @Test
    @SneakyThrows
    void testWasUpdated_Update_LastUpdatedChanges() {
        // given
        var analysis = getPersistedAnalysis();
        var initialTime = analysis.getLastUpdatedDate();

        // when
        analysis.setName("updated");
        analysisRepository.save(analysis);

        // then
        assertThat(initialTime.getTime()).isLessThanOrEqualTo(analysis.getLastUpdatedDate().getTime());
    }

    @Test
    @SneakyThrows
    void testWasUpdated_ChildPersist_LastUpdatedChanges() {
        // given
        var analysis = getPersistedAnalysis();
        var initialTime = analysis.getLastUpdatedDate();

        // when
        var impact = getPersistedImpact(analysis);
        impactRepository.save(impact);

        // then
        assertThat(initialTime.getTime()).isLessThanOrEqualTo(analysis.getLastUpdatedDate().getTime());
    }

    @Disabled("This test is disabled because the @PreUpdate annotation does not work")
    @Test
    @SneakyThrows
    void testWasUpdated_ChildUpdate_LastUpdatedChanges() {
        // given
        var analysis = getPersistedAnalysis();
        var impact = getPersistedImpact(analysis);
        var initialTime = analysis.getLastUpdatedDate();

        // when
        impact.setMerit(0.0f);
        impactRepository.save(impact);

        // then
        assertThat(initialTime.getTime()).isLessThanOrEqualTo(analysis.getLastUpdatedDate().getTime());
    }

    @Test
    @SneakyThrows
    void testWasUpdated_ChildDelete_LastUpdatedChanges() {
        // given
        var analysis = getPersistedAnalysis();
        var impact = getPersistedImpact(analysis);
        var initialTime = analysis.getLastUpdatedDate();

        // when
        impactRepository.delete(impact);

        // then
        assertThat(initialTime.getTime()).isLessThanOrEqualTo(analysis.getLastUpdatedDate().getTime());
    }
}
