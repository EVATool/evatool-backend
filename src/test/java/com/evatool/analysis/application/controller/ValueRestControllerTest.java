package com.evatool.analysis.application.controller;

import com.evatool.analysis.application.dto.ValueDto;
import com.evatool.analysis.application.services.ValueService;
import com.evatool.analysis.common.error.ValueType;
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

import static com.evatool.analysis.common.TestDataGenerator.*;
import static com.evatool.analysis.application.dto.ValueDtoMapper.toDto;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ValueRestControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ValueService valueService;

    @BeforeEach
    public void clearDatabase() {
        valueService.deleteAll();
    }

    private ValueDto saveFullDummyValueDto() {
        var value = createDummyValue();
        var valueDto = toDto(value);
        return valueService.create(valueDto);

    }

    @Nested
    class FindById {

        @Test
        void testFindById_CreatedValue_ReturnValue() {
            // given
            var valueDto = saveFullDummyValueDto();

            // when
            var response = testRestTemplate.getForEntity(
                    "/dimensions" + "/" + valueDto.getId().toString(), ValueDto.class);

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo(valueDto);
        }

        @Test
        void testFindById_NonExistingValues_ReturnHttpStatusNotFound() {
            // given
            var response = testRestTemplate.getForEntity(
                    "/dimensions" + "/" + UUID.randomUUID().toString(), ValueDto.class);

            // when

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }

    @Nested
    class FindAll {

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3})
        void testFindAll_ExistingValue_ReturnValue(int value) {
            for (int i = 0; i < value; i++) {
                // given
                saveFullDummyValueDto();
            }

            // when
            var response = testRestTemplate.getForEntity(
                    "/dimensions", ValueDto[].class);
            var valueDtoList = response.getBody();

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(valueDtoList).isNotNull().hasSize(value);
        }
    }

    @Nested
    class FindAllByType {

        @Test
        void testFindAllByType_ExistingValue_ReturnValue() {
            // given
            int n_socialValue = 3;
            for (int i = 0; i < n_socialValue; i++) {
                var socialValue = createDummyValueDto();
                socialValue.setType(ValueType.SOCIAL);
                valueService.create(socialValue);
            }

            int n_economicValue = 4;
            for (int i = 0; i < n_economicValue; i++) {
                var economicValue = createDummyValueDto();
                economicValue.setType(ValueType.ECONOMIC);
                valueService.create(economicValue);
            }

            // when
            var getSocialResponse = testRestTemplate.getForEntity(
                    "/dimensions" + "?type=" + ValueType.SOCIAL.toString(), ValueDto[].class);
            var socialValue = getSocialResponse.getBody();

            var getEconomicResponse = testRestTemplate.getForEntity(
                    "/dimensions" + "?type=" + ValueType.ECONOMIC.toString(), ValueDto[].class);
            var economicValue = getEconomicResponse.getBody();

            // then
            assertThat(socialValue).hasSize(n_socialValue);
            assertThat(economicValue).hasSize(n_economicValue);
        }
    }

    @Nested
    class findAllTypes {

        @Test
        void testFindAllTypes_ReturnAllPossibleTypes() {
            // given

            // when
            var valueTypes = testRestTemplate.getForEntity(
                    "/dimensions/types", ValueType[].class);

            // then
            assertThat(valueTypes.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(valueTypes.getBody()).isEqualTo(ValueType.values());
        }
    }

    @Nested
    class Create {

        @Test
        void testCreate_CreatedValue_ReturnCreatedValue() {
            // given
            var valuesDto = createDummyValueDto();

            // when
            var httpEntity = new HttpEntity<>(valuesDto);
            var response = testRestTemplate.postForEntity(
                    "/dimensions", httpEntity, ValueDto.class);

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        }

        @Test
        void testCreate_NotNullId_ReturnHttpStatusUnprocessableEntity() {
            // given
            var valuesDto = createDummyValueDto();
            valuesDto.setId(UUID.randomUUID());

            // when
            var httpEntity = new HttpEntity<>(valuesDto);
            var response = testRestTemplate.postForEntity(
                    "/dimensions", httpEntity, ValueDto.class);

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Nested
    class Update {

        @Test
        void testUpdate_CreatedValues_ReturnUpdatedValues() {
            // given
            var valuesDto = saveFullDummyValueDto();

            // when
            valuesDto.setName("new_name");
            var httpEntity = new HttpEntity<>(valuesDto);
            var response = testRestTemplate.exchange("/dimensions", HttpMethod.PUT, httpEntity, ValueDto.class);

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(valueService.findById(valuesDto.getId())).isEqualTo(valuesDto);
        }

        @Test
        void testUpdate_UpdateNonExistingId_ReturnHttpStatusNotFound() {
            // given
            var value = createDummyValue();
            value.setId(UUID.randomUUID());
            var dimensionDto = toDto(value);
            var httpEntity = new HttpEntity<>(dimensionDto);

            // when
            var response = testRestTemplate.exchange(
                    "/dimensions", HttpMethod.PUT, httpEntity, ValueDto.class);

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Test
        void testUpdate_UpdateNullId_ReturnHttpStatusUnprocessableEntity() {
            // given
            var valueDto = createDummyValueDto();

            // when
            var httpEntity = new HttpEntity<>(valueDto);
            var response = testRestTemplate.exchange(
                    "/dimensions", HttpMethod.PUT, httpEntity, ValueDto.class);

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @Nested
    class DeleteById {

        @Test
        void testDeleteById_ExistingDimension_ReturnHttpStatusOK() {
            // given
            var valueDto = saveFullDummyValueDto();

            // when
            var response = testRestTemplate.exchange(
                    "/dimensions" + "/" + valueDto.getId(), HttpMethod.DELETE, null, Void.class);
            var values = valueService.findAll();

            // then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(values).isEmpty();
        }

        @Test
        void testDeleteById_DeleteNonExistingId_ReturnHttpStatusNotFound() {
            // given
            var valueDto = createDummyValueDto();
            valueDto.setId(UUID.randomUUID());

            // when
            var response = testRestTemplate.exchange(
                    "/dimensions" + "/" + valueDto.getId(), HttpMethod.DELETE, null, Void.class);

            //then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }
    }
}
