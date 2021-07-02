package com.evatool.application.controller;

import com.evatool.application.dto.AnalysisChildDto;
import com.evatool.application.dto.SuperDto;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public interface FindByAnalysisControllerTest<T extends AnalysisChildDto> {

    SuperDto getPersistedDto();

    String getUri();

    TestRestTemplate getRest();

    Class<T[]> getDtoClassArray();

    @Test
    default void testAllFindAnalysisById() {
        // given
        var dto = (AnalysisChildDto) getPersistedDto();

        // when
        var response = getRest().getForEntity(getUri() + "?analysisId=" + dto.getAnalysisId(), getDtoClassArray());
        var dtoListFound = response.getBody();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(dtoListFound).hasSize(1);
    }
}
