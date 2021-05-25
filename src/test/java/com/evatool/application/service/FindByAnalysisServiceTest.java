package com.evatool.application.service;

import com.evatool.application.dto.AnalysisChildDto;
import com.evatool.application.dto.SuperDto;
import com.evatool.application.service.api.CrudService;
import com.evatool.application.service.api.FindByAnalysisService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public interface FindByAnalysisServiceTest {

    SuperDto getPersistedDto();

    CrudService getService();

    @Test
    default void testAllFindAnalysisById() {
        // given
        var dto = (AnalysisChildDto) getPersistedDto();

        // when
        var dtoListFound = ((FindByAnalysisService) getService()).findAllByAnalysisId(dto.getAnalysisId());

        // then
        assertThat(dtoListFound).hasSize(1);
    }
}
