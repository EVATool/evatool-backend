package com.evatool.application.controller;

import com.evatool.application.dto.RequirementDeltaDto;
import com.evatool.domain.entity.RequirementDelta;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;

class RequirementDeltaControllerRestTest extends CrudControllerRestTest<RequirementDelta, RequirementDeltaDto> implements FindByAnalysisControllerRestTest {

    @Test
    void testValidator() { // TODO rework or delete this

        var dto = getFloatingRequirementDeltaDto();

        var httpEntity = new HttpEntity<>(dto);
        var response = rest.postForEntity(UriUtil.REQUIREMENTS_DELTA, httpEntity, getDtoClass());

        System.out.println(response.getStatusCode());

    }

}
