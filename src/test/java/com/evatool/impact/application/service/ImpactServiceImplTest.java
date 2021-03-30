package com.evatool.impact.application.service;

import com.evatool.impact.application.dto.mapper.ImpactAnalysisDtoMapper;
import com.evatool.impact.application.dto.mapper.ImpactStakeholderDtoMapper;
import com.evatool.impact.application.dto.mapper.ImpactValueDtoMapper;
import com.evatool.impact.common.exception.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.UUID;

import static com.evatool.impact.application.dto.mapper.ImpactDtoMapper.toDto;
import static com.evatool.impact.common.TestDataGenerator.createDummyImpact;
import static com.evatool.impact.common.TestDataGenerator.createDummyImpactDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ImpactServiceImplTest extends ServiceTest {

    @Nested
    class FindById {

        @Test
        void testFindById_ExistingImpact_ReturnImpact() {
            // given
            var impact = saveFullDummyImpact();

            // when
            var impactDto = impactService.findById(impact.getId());

            // then
            assertThat(impactDto.toString()).isEqualTo(toDto(impact).toString());
        }

        @Test
        void testFindById_NonExistingId_ThrowEntityNotFoundException() {
            // given
            var id = UUID.randomUUID();

            // when

            // then
            assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> impactService.findById(id));
        }
    }

    @Nested
    class FindAll {

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3})
        void testFindAll_ExistingImpacts_ReturnImpact(int value) {
            // given
            for (int i = 0; i < value; i++) {
                saveFullDummyImpact();
            }

            // when
            var impacts = impactService.findAll();

            // then
            assertThat(impacts.size()).isEqualTo(value);
        }

        @Test
        void testFindAllByAnalysisId_AnalysisWithTwoImpacts_ReturnImpactsByAnalysisId() {
            // given
            var analysis = saveFullDummyAnalysis();
            var impact1 = saveFullDummyImpact(analysis);
            var impact2 = saveFullDummyImpact(analysis);

            // when

            // then
            var impactsOfAnalysis = impactService.findAllByAnalysisId(impact1.getAnalysis().getId());
            assertThat(impactsOfAnalysis.size()).isEqualTo(Arrays.asList(toDto(impact1), toDto(impact2)).size());
        }
    }

    @Nested
    class Create {

        @Test
        void testCreate_CreatedImpact_ReturnCreatedImpact() {
            // given
            var impactDto = createDummyImpactDto();
            impactDto.setValueEntity(ImpactValueDtoMapper.toDto(saveFullDummyValue()));
            impactDto.setStakeholder(ImpactStakeholderDtoMapper.toDto(saveFullDummyStakeholder()));
            impactDto.setAnalysis(ImpactAnalysisDtoMapper.toDto(saveFullDummyAnalysis()));

            // when
            var createdImpact = impactService.create(impactDto);
            var retrievedImpact = impactService.findById(createdImpact.getId());

            // then
            assertEquals(retrievedImpact.toString(), createdImpact.toString());
        }

        @Test
        void testCreate_ExistingId_ThrowEntityIdMustBeNullException() {
            // given
            var impactDto = createDummyImpactDto();

            // when
            impactDto.setId(UUID.randomUUID());

            // then
            assertThatExceptionOfType(EntityIdMustBeNullException.class).isThrownBy(() -> impactService.create(impactDto));
        }

        @Test
        void testCreate_ExistingUniqueString_ThrowUniqueStringMustBeNullException() {
            // given
            var impactDto = createDummyImpactDto();

            // when
            impactDto.setUniqueString("IMP42");

            // then
            assertThatExceptionOfType(UniqueStringMustBeNullException.class)
                    .isThrownBy(() -> impactService.create(impactDto));
        }
    }

    @Nested
    class Update {

        @Test
        void testUpdate_UpdatedImpact_ReturnUpdatedImpact() {
            // given
            var impact = saveFullDummyImpact();

            // when
            var newDescription = "new_desc";
            impact.setDescription(newDescription);
            impactService.update(toDto(impact));
            var impactDto = impactService.findById(impact.getId());

            // then
            assertThat(impactDto.getDescription()).isEqualTo(newDescription);
        }

        @Test
        void testUpdate_NonExistingId_ThrowEntityNotFoundException() {
            // given
            var impactDto = createDummyImpactDto();
            impactDto.setId(UUID.randomUUID());

            // when

            // then
            assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> impactService.update(impactDto));
        }

        @Test
        void testUpdate_NullId_ThrowEntityIdRequiredException() {
            // given
            var impactDto = createDummyImpactDto();

            // when

            // then
            assertThatExceptionOfType(EntityIdRequiredException.class).isThrownBy(() -> impactService.update(impactDto));
        }

        @Test
        void testUpdate_UpdateUniqueString_ThrowUniqueStringCannotBeUpdatedException() {
            // given
            var impactDto = toDto(saveFullDummyImpact());

            // when
            impactDto.setUniqueString(impactDto.getUniqueString() + "0");

            // then
            assertThatExceptionOfType(UniqueStringCannotBeUpdatedException.class)
                    .isThrownBy(() -> impactService.update(impactDto));
        }
    }

    @Nested
    class Delete {

        @Test
        void testDeleteById_DeleteImpact_ReturnNoImpacts() {
            // given
            var impact = saveFullDummyImpact();

            // when
            impactService.deleteById(impact.getId());

            // then
            var impacts = impactService.findAll();
            assertThat(impacts.size()).isZero();
        }

        @Test
        void testDeleteById_NonExistingId_ThrowEntityNotFoundException() {
            // given
            var impact = createDummyImpact();
            impact.setId(UUID.randomUUID());

            // when

            // then
            var id = impact.getId();
            assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> impactService.deleteById(id));
        }
    }

    @Nested
    class DeleteAll {

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3})
        void testDeleteAll_ExistingImpacts_ReturnNoImpact(int value) {
            // given
            for (int i = 0; i < value; i++) {
                saveFullDummyImpact();
            }

            // when
            impactService.deleteAll();

            // then
            var impacts = impactService.findAll();
            assertThat(impacts.size()).isZero();
        }
    }
}
