package com.evatool.application.mapper;

import com.evatool.application.dto.AnalysisDto;
import com.evatool.common.exception.PropertyIsInvalidException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class PrefixIdMapperTest {

    @Autowired
    private AnalysisDtoMapper mapper;

    @Test
    void testAmendFromDto_PrefixIdInvalid_Throw() {
        // given
        var dto = new AnalysisDto("", "", false, null, null, null);

        // when
        dto.setPrefixSequenceId("Invalid");

        // then
        assertThatExceptionOfType(PropertyIsInvalidException.class).isThrownBy(() -> mapper.fromDto(dto));
    }
}
