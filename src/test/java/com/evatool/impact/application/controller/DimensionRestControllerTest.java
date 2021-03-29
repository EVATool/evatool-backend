package com.evatool.impact.application.controller;

import com.evatool.impact.application.dto.DimensionDto;
import com.evatool.impact.application.service.DimensionService;
import com.evatool.impact.common.DimensionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
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
import static com.evatool.impact.application.controller.UriUtil.DIMENSION_TYPES;
import static com.evatool.impact.application.dto.mapper.DimensionDtoMapper.toDto;
import static com.evatool.impact.common.TestDataGenerator.createDummyDimension;
import static com.evatool.impact.common.TestDataGenerator.createDummyDimensionDto;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class DimensionRestControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private DimensionService dimensionService;

    @BeforeEach
    public void clearDatabase() {
        dimensionService.deleteAll();
    }

    private DimensionDto saveFullDummyDimensionDto() {
        var dimension = createDummyDimension();
        var dimensionDto = toDto(dimension);
        return dimensionService.create(dimensionDto);

    }

    @Nested
    class FindById {

        @Test
        void testFindById_CreatedDimension_ReturnDimension() {
            // given
            var dimensionDto = saveFullDummyDimensionDto();

            // when
            var response = testRestTemplate.getForEntity(
                    DIMENSIONS + "/" + dimensionDto.getId().toString(), DimensionDto.class);

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo(dimensionDto);
        }

        @Test
        void testFindById_NonExistingDimension_ReturnHttpStatusNotFound() {
            // given
            var response = testRestTemplate.getForEntity(
                    DIMENSIONS + "/" + UUID.randomUUID().toString(), DimensionDto.class);

            // when

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        // This code is technically testing spring code but it exists to ensure it is not broken.
        @Test
        void testFindById_InvalidUuid_ReturnHttpStatusBadRequest() {
            // given
            var response = testRestTemplate.getForEntity(
                    DIMENSIONS + "/123", DimensionDto.class);

            // when

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
    }

    @Nested
    class FindAll {

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3})
        void testFindAll_ExistingDimensions_ReturnDimensions(int value) {
            for (int i = 0; i < value; i++) {
                // given
                saveFullDummyDimensionDto();
            }

            // when
            var response = testRestTemplate.getForEntity(
                    DIMENSIONS, DimensionDto[].class);
            var dimensionDtoList = response.getBody();

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(dimensionDtoList).isNotNull().hasSize(value);
        }
    }

    @Nested
    class FindAllByType {

        @Test
        void testFindAllByType_ExistingDimension_ReturnDimensions() {
            // given
            int n_socialDimensions = 3;
            for (int i = 0; i < n_socialDimensions; i++) {
                var socialDimension = createDummyDimensionDto();
                socialDimension.setType(DimensionType.SOCIAL);
                dimensionService.create(socialDimension);
            }

            int n_economicDimensions = 4;
            for (int i = 0; i < n_economicDimensions; i++) {
                var economicDimension = createDummyDimensionDto();
                economicDimension.setType(DimensionType.ECONOMIC);
                dimensionService.create(economicDimension);
            }

            // when
            var getSocialResponse = testRestTemplate.getForEntity(
                    DIMENSIONS + "?type=" + DimensionType.SOCIAL.toString(), DimensionDto[].class);
            var socialDimensions = getSocialResponse.getBody();

            var getEconomicResponse = testRestTemplate.getForEntity(
                    DIMENSIONS + "?type=" + DimensionType.ECONOMIC.toString(), DimensionDto[].class);
            var economicDimensions = getEconomicResponse.getBody();

            // then
            assertThat(socialDimensions).hasSize(n_socialDimensions);
            assertThat(economicDimensions).hasSize(n_economicDimensions);
        }
    }

    @Nested
    class findAllTypes {

        @Test
        void testFindAllTypes_ReturnAllPossibleTypes() {
            // given

            // when
            var dimensionTypes = testRestTemplate.getForEntity(
                    DIMENSION_TYPES, DimensionType[].class);

            // then
            assertThat(dimensionTypes.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(dimensionTypes.getBody()).isEqualTo(DimensionType.values());
        }
    }

    @Nested
    class Create {

        @Test
        void testCreate_CreatedDimension_ReturnCreatedDimension() {
            // given
            var dimensionDto = createDummyDimensionDto();

            // when
            var httpEntity = new HttpEntity<>(dimensionDto);
            var response = testRestTemplate.postForEntity(
                    DIMENSIONS, httpEntity, DimensionDto.class);

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        }

        @Test
        void testCreate_NotNullId_ReturnHttpStatusUnprocessableEntity() {
            // given
            var dimensionDto = createDummyDimensionDto();
            dimensionDto.setId(UUID.randomUUID());

            // when
            var httpEntity = new HttpEntity<>(dimensionDto);
            var response = testRestTemplate.postForEntity(
                    DIMENSIONS, httpEntity, DimensionDto.class);

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Nested
    class Update {

        @Test
        void testUpdate_CreatedDimension_ReturnUpdatedDimension() {
            // given
            var dimensionDto = saveFullDummyDimensionDto();

            // when
            dimensionDto.setName("new_name");
            var httpEntity = new HttpEntity<>(dimensionDto);
            var response = testRestTemplate.exchange(DIMENSIONS, HttpMethod.PUT, httpEntity, DimensionDto.class);

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(dimensionService.findById(dimensionDto.getId())).isEqualTo(dimensionDto);
        }

        @Test
        void testUpdate_UpdateNonExistingId_ReturnHttpStatusNotFound() {
            // given
            var dimension = createDummyDimension();
            dimension.setId(UUID.randomUUID());
            var dimensionDto = toDto(dimension);
            var httpEntity = new HttpEntity<>(dimensionDto);

            // when
            var response = testRestTemplate.exchange(
                    DIMENSIONS, HttpMethod.PUT, httpEntity, DimensionDto.class);

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Test
        void testUpdate_UpdateNullId_ReturnHttpStatusUnprocessableEntity() {
            // given
            var dimensionDto = createDummyDimensionDto();

            // when
            var httpEntity = new HttpEntity<>(dimensionDto);
            var response = testRestTemplate.exchange(
                    DIMENSIONS, HttpMethod.PUT, httpEntity, DimensionDto.class);

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Nested
    class DeleteById {

        @Test
        void testDeleteById_ExistingDimension_ReturnHttpStatusOK() {
            // given
            var dimensionDto = saveFullDummyDimensionDto();

            // when
            var response = testRestTemplate.exchange(
                    DIMENSIONS + "/" + dimensionDto.getId(), HttpMethod.DELETE, null, Void.class);
            var dimensions = dimensionService.findAll();

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(dimensions).isEmpty();
        }

        @Test
        void testDeleteById_DeleteNonExistingId_ReturnHttpStatusNotFound() {
            // given
            var dimensionDto = createDummyDimensionDto();
            dimensionDto.setId(UUID.randomUUID());

            // when
            var response = testRestTemplate.exchange(
                    DIMENSIONS + "/" + dimensionDto.getId(), HttpMethod.DELETE, null, Void.class);

            //then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }
}
