package com.evatool.analysis.application.controller.service;


import com.evatool.analysis.application.services.ValueService;
import com.evatool.analysis.common.error.execptions.EntityNotFoundException;
import com.evatool.analysis.domain.model.Value;
import com.evatool.analysis.domain.repository.AnalysisRepository;
import com.evatool.analysis.domain.repository.ValueRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static com.evatool.analysis.application.dto.ValueDtoMapper.toDto;
import static com.evatool.analysis.common.TestDataGenerator.createDummyValue;
import static com.evatool.analysis.common.TestDataGenerator.getAnalysis;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ValueServiceImplTest {

    @Autowired
    ValueService valueService;

    @Autowired
    ValueRepository valueRepository;

    @Autowired
    AnalysisRepository analysisRepository;

    private Value saveFullDummyValue() {
        var value = createDummyValue();
        var analysis = analysisRepository.save(getAnalysis());
        value.setAnalysis(analysis);
        return valueRepository.save(value);
    }

    @BeforeEach
    @AfterAll
    private void clearDatabase() {
        valueRepository.deleteAll();
        analysisRepository.deleteAll();
    }

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
                System.out.println("loop");
            }

            // when
            var values = valueService.findAll();

            // then
            assertThat(values.size()).isEqualTo(value);
        }
    }
}
