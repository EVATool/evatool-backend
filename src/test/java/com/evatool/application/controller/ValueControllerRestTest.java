package com.evatool.application.controller;

import com.evatool.application.dto.ValueDto;
import com.evatool.common.enums.ValueType;
import com.evatool.common.util.UriUtil;
import com.evatool.domain.entity.Value;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class ValueControllerRestTest extends CrudControllerRestTest<Value, ValueDto> implements FindByAnalysisControllerRestTest {

    @Test
    void testGetValueTypes() {
        // given

        // when
        var valueTypes = rest.getForEntity(UriUtil.VALUES_TYPES, ValueType[].class);

        // then
        assertThat(valueTypes.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(valueTypes.getBody()).hasSize(ValueType.values().length);
        assertThat(valueTypes.getBody()).isEqualTo(ValueType.values());
    }
}
