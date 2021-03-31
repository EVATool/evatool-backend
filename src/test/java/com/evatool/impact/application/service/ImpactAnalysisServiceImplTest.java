package com.evatool.impact.application.service;

import com.evatool.impact.common.exception.EntityNotFoundException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import static com.evatool.impact.application.dto.mapper.ImpactAnalysisDtoMapper.toDto;
import static com.evatool.impact.common.TestDataGenerator.createDummyAnalysis;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ImpactAnalysisServiceImplTest extends ServiceTest {

    @Nested
    class FindById {

        @Test
        void testFindById_ExistingAnalysis_ReturnAnalysis() {
            // given
            var analysis = saveFullDummyAnalysis();

            // when
            var analysisDto = analysisService.findById(analysis.getId());

            // then
            assertThat(analysisDto).isEqualTo(toDto(analysis));
        }

        @Test
        void testFindById_NonExistingId_ThrowEntityNotFoundException() {
            // given
            var analysis = createDummyAnalysis();

            // when

            // then
            var id = analysis.getId();
            assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> analysisService.findById(id));
        }
    }

    @Nested
    class DeleteAll {

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3})
        void testDeleteAll_ExistingAnalyses_ReturnNoAnalyses(int value) {
            // given
            for (int i = 0; i < value; i++) {
                saveFullDummyAnalysis();
            }

            // when
            analysisService.deleteAll();

            // then
            var analyses = analysisService.findAll();
            assertThat(analyses.size()).isZero();
        }
    }
}
