package com.evatool.domain.repository;

import com.evatool.domain.entity.FindByAnalysis;
import com.evatool.domain.entity.SuperEntity;
import org.junit.jupiter.api.Test;
import org.springframework.data.repository.CrudRepository;

import static org.assertj.core.api.Assertions.assertThat;

public interface FindByAnalysisRepositoryTest {

    SuperEntity getPersistedEntity();

    CrudRepository getRepository();

    @Test
    default void testFindAllByAnalysisId() {
        // given
        var entity = (FindByAnalysis) getPersistedEntity();

        // when
        var entityListFound = ((FindByAnalysisRepository) getRepository()).findAllByAnalysisId(entity.getAnalysis().getId());

        // then
        assertThat(entityListFound).hasSize(1);
    }
}
