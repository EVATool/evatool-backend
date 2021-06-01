package com.evatool.application.mapper;

import com.evatool.application.dto.RequirementDeltaDto;
import com.evatool.domain.entity.RequirementDelta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RequirementDeltaMapperTest extends SuperMapperTest<RequirementDelta, RequirementDeltaDto, RequirementDeltaMapper> {

    @Autowired
    private RequirementDeltaMapper mapper;

    @Test
    void testToDto_NegativeImpact_CorrectMinMax() {
        // given
        var delta = getPersistedRequirementDelta();
        delta.getImpact().setMerit(-0.5f);
        delta.setOverwriteMerit(-0.2f);

        // when
        var dto = mapper.toDto(delta);

        // then
        assertThat(dto.getMinOverwriteMerit()).isEqualTo(-0.5f);
        assertThat(dto.getMaxOverwriteMerit()).isEqualTo(0f);
    }

    @Test
    void testToDto_PositiveImpact_CorrectMinMax() {
        // given
        var delta = getPersistedRequirementDelta();
        delta.getImpact().setMerit(0.5f);
        delta.setOverwriteMerit(0.2f);

        // when
        var dto = mapper.toDto(delta);

        // then
        assertThat(dto.getMinOverwriteMerit()).isEqualTo(0f);
        assertThat(dto.getMaxOverwriteMerit()).isEqualTo(0.5f);
    }

    // TODO colorToHex tests

}
