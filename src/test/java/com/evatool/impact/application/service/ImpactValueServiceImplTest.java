package com.evatool.impact.application.service;

import com.evatool.impact.common.exception.EntityNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static com.evatool.impact.application.dto.mapper.ImpactValueDtoMapper.toDto;
import static com.evatool.impact.common.TestDataGenerator.createDummyValue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ImpactValueServiceImplTest extends ServiceTest {

    @Nested
    class FindById {

        @Test
        void testFindById_ExistingValue_ReturnValue() {
            // given
            var value = saveFullDummyValue();

            // when
            var valueDto = valueService.findById(value.getId());

            // then
            assertThat(valueDto).isEqualTo(toDto(value));
        }

        @Test
        void testFindById_NonExistingId_ThrowEntityNotFoundException() {
            // given
            var value = createDummyValue();

            // when

            // then
            var id = value.getId();
            assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> valueService.findById(id));
        }

        @Test
        void testFindByAnalysisId_ExistingAnalysisId_ReturnValue() {
            // given
            var analysis = saveFullDummyAnalysis();
            var impact = saveFullDummyImpact(analysis);

            // when
            var analysisId = analysis.getId();
            var values = valueService.findAllByAnalysisId(analysisId);

            // then
            var impactValue = impact.getValueEntity();
            assertThat(values).contains(toDto(impactValue));
        }

        @Test
        void testFindByAnalysisId_NonExistingAnalysisId_ReturnEmptyList() {
            // given
            var notAValidId = UUID.randomUUID();

            // when
            var values = valueService.findAllByAnalysisId(notAValidId);

            // then
            assertThat(values).isEmpty();
        }
    }

    @Nested
    class FindAll {

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3})
        void testFindAll_ExistingValues_ReturnValues(int value) {
            // given
            for (int i = 0; i < value; i++) {
                saveFullDummyValue();
            }

            // when
            var values = valueService.findAll();

            // then
            assertThat(values.size()).isEqualTo(value);
        }
    }

    @Nested
    class DeleteAll {

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3})
        void testDeleteAll_ExistingValues_ReturnNoValues(int value) {
            // given
            for (int i = 0; i < value; i++) {
                saveFullDummyValue();
            }

            // when
            valueService.deleteAll();

            // then
            var values = valueService.findAll();
            assertThat(values.size()).isZero();
        }
    }
}
