package com.evatool.requirements.application.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class RequirementDTOTest {

    @Test
    void testSetRootEntityId_NullValue_ThrowException() {
        // given
        RequirementDTO requirementDTO = new RequirementDTO();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirementDTO.setRootEntityId(null));
    }

    @Test
    void testSetRequirementTitle_NullValue_ThrowException() {
        // given
        RequirementDTO requirementDTO = new RequirementDTO();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirementDTO.setRequirementTitle(null));
    }

    @Test
    void testSetRequirementDescription_NullValue_ThrowException() {
        // given
        RequirementDTO requirementDTO = new RequirementDTO();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirementDTO.setRequirementDescription(null));
    }

    @Test
    void testSetDimensions_NullValue_ThrowException() {
        // given
        RequirementDTO requirementDTO = new RequirementDTO();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirementDTO.setDimensions(null));
    }

    @Test
    void testSetVariantsTitle_NullValue_ThrowException() {
        // given
        RequirementDTO requirementDTO = new RequirementDTO();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirementDTO.setVariantsTitle(null));
    }

    @Test
    void testSetRequirementImpactPoints_NullValue_ThrowException() {
        // given
        RequirementDTO requirementDTO = new RequirementDTO();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirementDTO.setRequirementImpactPoints(null));
    }

    @Test
    void testSetProjectID_NullValue_ThrowException() {
        // given
        RequirementDTO requirementDTO = new RequirementDTO();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> requirementDTO.setProjectID(null));
    }


}
