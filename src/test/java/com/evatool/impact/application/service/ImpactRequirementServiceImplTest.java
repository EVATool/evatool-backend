package com.evatool.impact.application.service;

import com.evatool.impact.common.exception.EntityNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.evatool.impact.application.dto.mapper.ImpactRequirementDtoMapper.toDto;
import static com.evatool.impact.common.TestDataGenerator.createDummyRequirement;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ImpactRequirementServiceImplTest extends ServiceTest {

    @Nested
    class FindById {

        @Test
        void testFindById_ExistingRequirement_ReturnRequirement() {
            // given
            var requirement = saveFullDummyRequirement();

            // then
            var requirementDto = requirementService.findById(requirement.getId());

            // when
            assertThat(requirementDto).isEqualTo(toDto(requirement));
        }

        @Test
        void testFindById_NonExistingId_ThrowEntityNotFoundException() {
            // given
            var requirement = createDummyRequirement();

            // then

            // when
            var id = requirement.getId();
            assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> requirementService.findById(id));
        }
    }

    @Nested
    class FindAll {

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3})
        void testFindAll_ExistingRequirements_ReturnRequirements(int value) {
            // given
            for (int i = 0; i < value; i++) {
                saveFullDummyRequirement();
            }

            // then
            var requirements = requirementService.findAll();

            // when
            assertThat(requirements.size()).isEqualTo(value);
        }
    }

    @Nested
    class DeleteAll {
        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3})
        void testDeleteAll_ExistingRequirements_ReturnNoRequirements(int value) {
            // given
            for (int i = 0; i < value; i++) {
                saveFullDummyRequirement();
            }

            // then
            requirementService.deleteAll();

            // when
            var requirements = requirementService.findAll();
            assertThat(requirements).isEmpty();
        }
    }
}
