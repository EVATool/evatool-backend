package com.evatool.analysis.application.controller.service;


import com.evatool.impact.application.service.ImpactValueService;
import com.evatool.impact.application.service.ServiceTest;
import com.evatool.impact.common.exception.EntityNotFoundException;
import com.evatool.impact.domain.repository.ImpactValueRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static com.evatool.impact.application.dto.mapper.ImpactValueDtoMapper.toDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


class ImpactValueServiceImplTest extends ServiceTest {

    @Autowired
    ImpactValueService valueService;

    @Autowired
    ImpactValueRepository valueRepository;

    @Nested
    class FindById {

        @Test
        void testFindById_ExistingValue_ReturnValues() {
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
            var id = UUID.randomUUID();

            // when

            // then
            assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> valueService.findById(id));
        }
    }

    @Nested
    class FindAll {

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3})
        void testFindAll_CreatedValues_ReturnValue(int value) {
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
}
