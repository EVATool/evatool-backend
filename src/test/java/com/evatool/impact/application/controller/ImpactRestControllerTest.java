package com.evatool.impact.application.controller;

import com.evatool.impact.application.dto.ImpactDto;
import com.evatool.impact.application.dto.mapper.DimensionDtoMapper;
import com.evatool.impact.application.dto.mapper.ImpactDtoMapper;
import com.evatool.impact.application.dto.mapper.ImpactStakeholderDtoMapper;
import com.evatool.impact.application.service.ImpactService;
import com.evatool.impact.domain.repository.DimensionRepository;
import com.evatool.impact.domain.repository.ImpactStakeholderRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static com.evatool.impact.application.controller.UriUtil.DIMENSIONS;
import static com.evatool.impact.application.controller.UriUtil.IMPACTS;
import static com.evatool.impact.application.dto.mapper.ImpactDtoMapper.toDto;
import static com.evatool.impact.common.TestDataGenerator.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ImpactRestControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ImpactService impactService;

    @Autowired
    private DimensionRepository dimensionRepository;

    @Autowired
    private ImpactStakeholderRepository stakeholderRepository;

    @BeforeEach
    @AfterAll
    private void clearDatabase() {
        impactService.deleteImpacts();
        stakeholderRepository.deleteAll();
        dimensionRepository.deleteAll();
    }

    private ImpactDto saveFullDummyImpactDto() {
        var impact = createDummyImpact();
        impact.setDimension(dimensionRepository.save(impact.getDimension()));
        impact.setStakeholder(stakeholderRepository.save(impact.getStakeholder()));
        return impactService.createImpact(toDto(impact));
    }

    private ImpactDto saveDummyImpactDtoChildren() {
        var impactDto = createDummyImpactDto();
        impactDto.getDimension().setId(UUID.randomUUID());
        dimensionRepository.save(DimensionDtoMapper.fromDto(impactDto.getDimension()));
        impactDto.getStakeholder().setId(UUID.randomUUID());
        stakeholderRepository.save(ImpactStakeholderDtoMapper.fromDto(impactDto.getStakeholder()));
        return impactDto;
    }

    @Nested
    class GetById {

        @Test
        void testGetImpactById_InsertedImpact_ReturnImpact() {
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
        void testGetImpactById_NonExistingImpact_ReturnHttpStatusNotFound() {
            // given
            var response = testRestTemplate.getForEntity(
                    IMPACTS + "/" + UUID.randomUUID().toString(), ImpactDto.class);

            // when

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

    @Nested
    class GetAll {

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3, 4, 5})
        void testGetImpacts_ExistingImpacts_ReturnImpacts(int value) {
            for (int i = 0; i < value; i++) {
                // given
                saveFullDummyImpactDto();
            }

            // when
            var getResponse = testRestTemplate.getForEntity(
                    IMPACTS, ImpactDto[].class);
            var impactDtoList = getResponse.getBody();

            // then
            assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(impactDtoList).isNotNull().hasSize(value);
        }
    }

    @Nested
    class Insert {

        @Test
        void testInsertImpact_InsertImpact_ReturnInsertedImpact() {
            // given
            var impactDto = saveDummyImpactDtoChildren();

            // when
            var httpEntity = new HttpEntity<>(impactDto);
            var responseEntity = testRestTemplate.postForEntity(
                    IMPACTS, httpEntity, ImpactDto.class);

            // then
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        }

        @Test
        void testInsertImpact_InsertEmptyImpactDto_ReturnHttpStatusBadRequest() {
            // given
            var httpEntity = new HttpEntity<>(new ImpactDto());

            // when
            var responseEntity = testRestTemplate.postForEntity(
                    IMPACTS, httpEntity, ImpactDto.class);

            // then
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        void testInsertImpact_InsertImpactWithIllegalValue_ReturnHttpStatusBadRequest() {
            // given
            var impactDto = saveDummyImpactDtoChildren();
            impactDto.setValue(2);

            // when
            var httpEntity = new HttpEntity<>(impactDto);
            var responseEntity = testRestTemplate.postForEntity(
                    IMPACTS, httpEntity, ImpactDto.class);

            // then
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        void testInsertImpact_InsertImpactWithNullDescription_ReturnHttpStatusBadRequest() {
            // given
            var impactDto = saveDummyImpactDtoChildren();
            impactDto.setDescription(null);

            // when
            var httpEntity = new HttpEntity<>(impactDto);
            var responseEntity = testRestTemplate.postForEntity(
                    IMPACTS, httpEntity, ImpactDto.class);

            // then
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        void testInsertImpact_InsertNotNullId_ReturnHttpStatusUnprocessableEntity() {
            // given
            var impactDto = saveDummyImpactDtoChildren();
            impactDto.setId(UUID.randomUUID());

            // when
            var httpEntity = new HttpEntity<>(impactDto);
            var responseEntity = testRestTemplate.postForEntity(
                    IMPACTS, httpEntity, ImpactDto.class);

            // then
            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Nested
    class Update {

        @Test
        void testUpdateImpact_InsertedImpact_ReturnUpdatedImpact() {
            // given
            var impactDto = saveFullDummyImpactDto();

            // when
            impactDto.setDescription("new_desc");
            var putEntity = new HttpEntity<>(impactDto);
            var response = testRestTemplate.exchange(IMPACTS, HttpMethod.PUT, putEntity, ImpactDto.class);

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(impactService.findImpactById(impactDto.getId())).isEqualTo(impactDto);
        }

        @Test
        void testUpdateImpact_UpdateNonExistingId_ReturnHttpStatusNotFound() {
            // given
            var impact = createDummyImpact();
            impact.setId(UUID.randomUUID());
            var impactDto = ImpactDtoMapper.toDto(impact);
            var httpEntity = new HttpEntity<>(impactDto);

            // when
            var putResponse = testRestTemplate.exchange(
                    IMPACTS, HttpMethod.PUT, httpEntity, ImpactDto.class);

            // then
            assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Test
        void testUpdateImpact_UpdateIllegalValue_ReturnHttpStatusBadRequest() {
            // given
            var impactDto = saveFullDummyImpactDto();

            // when
            impactDto.setValue(2);
            var putEntity = new HttpEntity<>(impactDto);
            var putResponse = testRestTemplate.exchange(
                    IMPACTS, HttpMethod.PUT, putEntity, ImpactDto.class);

            // then
            assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        void testUpdateImpact_UpdateNullDescription_ReturnHttpStatusBadRequest() {
            // given
            var impactDto = saveFullDummyImpactDto();

            // when
            impactDto.setDescription(null);
            var putEntity = new HttpEntity<>(impactDto);
            var response = testRestTemplate.exchange(
                    IMPACTS, HttpMethod.PUT, putEntity, ImpactDto.class);

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }

        @Test
        void testUpdateImpact_UpdateNullId_ReturnHttpStatusUnprocessableEntity() {
            // given
            var impactDto = createDummyImpactDto();

            // when
            var httpEntity = new HttpEntity<>(impactDto);
            var putResponse = testRestTemplate.exchange(
                    IMPACTS, HttpMethod.PUT, httpEntity, ImpactDto.class);

            // then
            assertThat(putResponse.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        @Nested
        class ChildEntity {

            @Test
            void testUpdateChildEntity_UpdateExistingDimension_() {

            }

            @Test
            void testUpdateChildEntity_UpdateNonExistingDimension_() {

            }

            @Test
            void testUpdateChildEntity_UpdateExistingStakeholder_() {

            }

            @Test
            void testUpdateChildEntity_UpdateNonExistingStakeholder_() {

            }

        }
    }

    @Nested
    class Delete {

        @Test
        void testDeleteImpact_ExistingImpact_DeleteImpactAndReturnHttpStatusOK() {
            // given
            var impactDto = saveFullDummyImpactDto();

            // when
            var response = testRestTemplate.exchange(
                    IMPACTS + "/" + impactDto.getId(), HttpMethod.DELETE, null, Void.class);
            var impacts = impactService.getAllImpacts();

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(impacts).isEmpty();
        }

        @Test
        void testDeleteImpact_DeleteNonExistingId_ReturnHttpStatusNotFound() {
            // given
            var impactDto = saveFullDummyImpactDto();
            impactDto.setId(UUID.randomUUID());

            // when
            var response = testRestTemplate.exchange(
                    IMPACTS + "/" + impactDto.getId(), HttpMethod.DELETE, null, Void.class);

            //then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Nested
        class ChildEntity {

            @Test
            void testChildDeleted_DimensionChildDeleted_ReturnHttpStatusConflict() {
                // given
                var impactDto = saveFullDummyImpactDto();

                // when
                var response = testRestTemplate.exchange(
                        DIMENSIONS + "/" + impactDto.getDimension().getId(), HttpMethod.DELETE, null, Void.class);

                // then
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
            }
        }
    }
}
