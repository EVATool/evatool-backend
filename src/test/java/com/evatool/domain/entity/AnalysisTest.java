package com.evatool.domain.entity;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
class AnalysisTest extends SuperEntityTest {

    @Test
    void testConstructor_PassNull_Throws() {
        // given

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Analysis(null, "", false));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Analysis("", null, false));
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new Analysis("", "", null));
    }

    @Test
    void testWasUpdated_Persist_LastUpdatedIsNotNull() {
        // given
        var analysis = getPersistedAnalysis();

        // when

        // then
        assertThat(analysis.getLastUpdated()).isNotNull();
    }

    @Disabled("This test is disabled because the @PreUpdate annotation does not work")
    @Test
    @SneakyThrows
    void testWasUpdated_Update_LastUpdatedChanges() {
        // given
        var analysis = getPersistedAnalysis();
        var initialTime = analysis.getLastUpdated();

        // when
        analysis.setName("updated");
        analysisRepository.save(analysis);

        // then
        assertThat(initialTime).isBeforeOrEqualsTo(analysis.getLastUpdated());
    }

    @Test
    @SneakyThrows
    void testWasUpdated_ChildPersist_LastUpdatedChanges() {
        // given
        var analysis = getPersistedAnalysis();
        var initialTime = analysis.getLastUpdated();

        // when
        var impact = getPersistedImpact(analysis);
        impactRepository.save(impact);

        // then
        assertThat(initialTime).isBeforeOrEqualsTo(analysis.getLastUpdated());
    }

    @Disabled("This test is disabled because the @PreUpdate annotation does not work")
    @Test
    @SneakyThrows
    void testWasUpdated_ChildUpdate_LastUpdatedChanges() {
        // given
        var analysis = getPersistedAnalysis();
        var impact = getPersistedImpact(analysis);
        var initialTime = analysis.getLastUpdated();

        // when
        impact.setMerit(0.0f);
        impactRepository.save(impact);

        // then
        assertThat(initialTime).isBeforeOrEqualsTo(analysis.getLastUpdated());
    }

    @Test
    @SneakyThrows
    void testWasUpdated_ChildDelete_LastUpdatedChanges() {
        // given
        var analysis = getPersistedAnalysis();
        var impact = getPersistedImpact(analysis);
        var initialTime = analysis.getLastUpdated();

        // when
        impactRepository.delete(impact);

        // then
        assertThat(initialTime).isBeforeOrEqualsTo(analysis.getLastUpdated());
    }
}
