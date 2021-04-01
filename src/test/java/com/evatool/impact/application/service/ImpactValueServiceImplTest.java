package com.evatool.impact.application.service;

import com.evatool.impact.common.exception.EntityNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
