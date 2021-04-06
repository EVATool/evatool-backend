package com.evatool.impact.application.service;

import com.evatool.impact.common.exception.EntityNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.evatool.impact.application.dto.mapper.ImpactStakeholderDtoMapper.toDto;
import static com.evatool.impact.common.TestDataGenerator.createDummyStakeholder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ImpactStakeholderServiceImplTest extends ServiceTest {

    @Nested
    class FindById {

        @Test
        void testFindById_ExistingStakeholder_ReturnStakeholder() {
            // given
            var stakeholder = saveFullDummyStakeholder();

            // when
            var stakeholderDto = stakeholderService.findById(stakeholder.getId());

            // then
            assertThat(stakeholderDto).isEqualTo(toDto(stakeholder));
        }

        @Test
        void testFindById_NonExistingId_ThrowEntityNotFoundException() {
            // given
            var stakeholder = createDummyStakeholder();

            // when

            // then
            var id = stakeholder.getId();
            assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> stakeholderService.findById(id));
        }
    }

    @Nested
    class FindAll {

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3})
        void testFindAll_ExistingStakeholders_ReturnStakeholders(int value) {
            // given
            for (int i = 0; i < value; i++) {
                saveFullDummyStakeholder();
            }

            // when
            var stakeholders = stakeholderService.findAll();

            // then
            assertThat(stakeholders.size()).isEqualTo(value);
        }
    }

    @Nested
    class DeleteAll {

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3})
        void testDeleteAll_ExistingStakeholders_ReturnNoStakeholders(int value) {
            // given
            for (int i = 0; i < value; i++) {
                saveFullDummyStakeholder();
            }

            // when
            stakeholderService.deleteAll();

            // then
            var stakeholders = stakeholderService.findAll();
            assertThat(stakeholders.size()).isZero();
        }
    }
}
