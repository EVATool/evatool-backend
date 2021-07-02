package com.evatool.application.controller;

import com.evatool.application.controller.impl.AnalysisControllerImpl;
import com.evatool.application.dto.AnalysisDto;
import com.evatool.domain.entity.Analysis;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class AnalysisControllerTest extends CrudControllerTest<Analysis, AnalysisDto> {

    @Autowired
    private AnalysisControllerImpl controller;

    @Test
    void testFindAll() {
        // given
        getPersistedAnalysis();
        getPersistedAnalysis();
        getPersistedAnalysis();

        // when
        var response = rest.getForEntity(getUri(), AnalysisDto[].class);

        // then
        assertThat(response.getBody()).hasSize(3);
    }

    @Test
    void testDeepCopy() {
        // given
        var templateAnalysis = getPersistedAnalysis();
        var templateValue1 = getPersistedValue(templateAnalysis);
        var templateValue2 = getPersistedValue(templateAnalysis);
        var templateValue3 = getPersistedValue(templateAnalysis);

        // when
        var newAnalysisDto = analysisMapper.toDto(getFloatingAnalysis());
        var response = rest.postForEntity(getUri() + "/deep-copy/" + templateAnalysis.getId(), newAnalysisDto, AnalysisDto.class);
        var deepCopyAnalysis = response.getBody();

        // then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(deepCopyAnalysis).isNotNull();
        assertThat(valueRepository.findAllByAnalysisId(deepCopyAnalysis.getId())).hasSize(3);
    }
}
