package com.evatool.impact.application.controller;

import com.evatool.impact.application.dto.ImpactDto;
import com.evatool.impact.application.dto.mapper.ImpactDtoMapper;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import java.util.UUID;
import static com.evatool.impact.application.controller.UriUtil.IMPACTS;
import static com.evatool.impact.common.TestDataGenerator.createDummyImpact;
import static com.evatool.impact.common.TestDataGenerator.createDummyImpactDto;
import static org.assertj.core.api.Assertions.assertThat;

public class ImpactRestControllerTest extends ControllerTest {

    @Nested
    class FindById {

        @Test
        void testFindById_CreatedImpact_ReturnImpact() {
            // given
            var impactDto = saveFullDummyImpactDto();

            // when
            var response = testRestTemplate.getForEntity(
                    IMPACTS + "/" + impactDto.getId().toString(), ImpactDto.class);

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo(impactDto);
        }

        @Test
        void testFindById_NonExistingImpact_ReturnHttpStatusNotFound() {
            // given
            var response = testRestTemplate.getForEntity(
                    IMPACTS + "/" + UUID.randomUUID().toString(), ImpactDto.class);

            // when

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

    @Nested
    class FindAll {

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3})
        void testFindAll_ExistingEntities_ReturnEntities(int value) {
            for (int i = 0; i < value; i++) {
                // given
                saveFullDummyImpactDto();
            }

            // when
            var response = testRestTemplate.getForEntity(
                    IMPACTS, ImpactDto[].class);
            var impactDtoList = response.getBody();

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(impactDtoList).isNotNull().hasSize(value);
        }

        @Test
        void testFindAllByAnalysisId_AnalysisWithTwoImpacts_ReturnImpactsByAnalysisId() {
            // given
            var impact1 = saveFullDummyImpactDto();
            var impact2 = saveFullDummyImpactDto();

            // when
            impact2.setAnalysis(impact1.getAnalysis());
            impactService.update(impact2);

            var response = testRestTemplate.getForEntity(
                    IMPACTS + "?analysisId=" + impact1.getAnalysis().getId(), ImpactDto[].class);
            var impactsOfAnalysis = response.getBody();

            // then
            assertThat(impactsOfAnalysis.toString()).isEqualTo((new ImpactDto[]{impact1, impact2}).toString());
        }
    }

    @Nested
    class Create {

        @Test
        void testCreate_CreatedImpact_ReturnCreatedImpact() {
            try {
                // given
                var impactDto = createDummyImpactDto();

                // when
                var httpEntity = new HttpEntity<>(impactDto);
                var response = testRestTemplate.postForEntity(
                        IMPACTS, httpEntity, ImpactDto.class);

                // then
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
            }catch (Exception e){
                System.out.println(e);
            }
        }

        @Test
        void testCreate_NotNullId_ReturnHttpStatusUnprocessableEntity() {
            // given
            var impactDto = saveDummyImpactDtoChildren();
            impactDto.setId(UUID.randomUUID());

            // when
            var httpEntity = new HttpEntity<>(impactDto);
            var response = testRestTemplate.postForEntity(
                    IMPACTS, httpEntity, ImpactDto.class);

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        @Test
        void testCreate_NotNullUniqueString_ReturnHttpStatusUnprocessableEntity() {
            // given
            var impactDto = saveDummyImpactDtoChildren();

            // when
            impactDto.setUniqueString("here but shouldn't");
            var httpEntity = new HttpEntity<>(impactDto);
            var response = testRestTemplate.postForEntity(
                    IMPACTS, httpEntity, ImpactDto.class);

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Nested
    class Update {

        @Test
        void testUpdate_CreatedImpact_ReturnUpdatedImpact() {
            // given
            var impactDto = saveFullDummyImpactDto();

            // when
            impactDto.setDescription("new_desc");
            var httpEntity = new HttpEntity<>(impactDto);
            var response = testRestTemplate.exchange(IMPACTS, HttpMethod.PUT, httpEntity, ImpactDto.class);

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(impactService.findById(impactDto.getId())).isEqualTo(impactDto);
        }

        @Test
        void testUpdate_UpdateNonExistingId_ReturnHttpStatusNotFound() {
            // given
            var impact = createDummyImpact();
            impact.setId(UUID.randomUUID());
            var impactDto = ImpactDtoMapper.toDto(impact);
            var httpEntity = new HttpEntity<>(impactDto);

            // when
            var response = testRestTemplate.exchange(
                    IMPACTS, HttpMethod.PUT, httpEntity, ImpactDto.class);

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Test
        void testUpdate_UpdateNullId_ReturnHttpStatusUnprocessableEntity() {
            // given
            var impactDto = createDummyImpactDto();

            // when
            var httpEntity = new HttpEntity<>(impactDto);
            var response = testRestTemplate.exchange(
                    IMPACTS, HttpMethod.PUT, httpEntity, ImpactDto.class);

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        @Test
        void testUpdate_UpdateNumericId_ReturnHttpStatusUnprocessableEntity() {
            // given
            var impactDto = saveFullDummyImpactDto();

            // when
            impactDto.setUniqueString(impactDto.getUniqueString() + "/");
            var httpEntity = new HttpEntity<>(impactDto);
            var response = testRestTemplate.exchange(IMPACTS, HttpMethod.PUT, httpEntity, ImpactDto.class);

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Nested
    class DeleteById {

        @Test
        void testDeleteById_ExistingImpact_ReturnHttpStatusOK() {
            // given
            var impactDto = saveFullDummyImpactDto();

            // when
            var response = testRestTemplate.exchange(
                    IMPACTS + "/" + impactDto.getId(), HttpMethod.DELETE, null, Void.class);
            var impacts = impactService.findAll();

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(impacts).isEmpty();
        }

        @Test
        void testDeleteById_DeleteNonExistingId_ReturnHttpStatusNotFound() {
            // given
            var impactDto = saveFullDummyImpactDto();
            impactDto.setId(UUID.randomUUID());

            // when
            var response = testRestTemplate.exchange(
                    IMPACTS + "/" + impactDto.getId(), HttpMethod.DELETE, null, Void.class);

            //then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }
}
