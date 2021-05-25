package com.evatool.application.controller;

import com.evatool.application.controller.api.CrudController;
import com.evatool.application.controller.api.FindByAnalysisController;
import com.evatool.application.dto.AnalysisChildDto;
import com.evatool.application.dto.SuperDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public interface FindByAnalysisControllerTest {

    SuperDto getPersistedDto();

    CrudController getController();

    @Test
    default void testAllFindAnalysisById() {
        // given
        var dto = (AnalysisChildDto) getPersistedDto();

        // when
        var response = ((FindByAnalysisController) getController()).findAllByAnalysisId(dto.getAnalysisId());
        var dtoListFound = (Iterable) response.getBody();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(dtoListFound).hasSize(1);
    }
}
