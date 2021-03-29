package com.evatool.analysis.application.controller.service;

import com.evatool.analysis.application.services.ValueService;
import com.evatool.analysis.common.error.ValueType;
import com.evatool.analysis.common.error.execptions.EntityIdMustBeNullException;
import com.evatool.analysis.common.error.execptions.EntityIdRequiredException;
import com.evatool.analysis.common.error.execptions.EntityNotFoundException;
import com.evatool.analysis.domain.model.Value;
import com.evatool.analysis.domain.repository.ValueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.UUID;

import static com.evatool.analysis.application.dto.ValueDtoMapper.toDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static com.evatool.analysis.common.TestDataGenerator.*;


@SpringBootTest
class ImpactValueServiceImplTest {

    @Autowired
    ValueService valueService;

    @Autowired
    ValueRepository valueRepository;

    @BeforeEach
    void clearDatabase() {
        valueService.deleteAll();
    }

    private Value saveFullDummyValue() {
        var value = createDummyValue();
        return valueRepository.save(value);
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
            var value = createDummyValue();
            value.setId(UUID.randomUUID());

            // when

            // then
            var id = value.getId();
            assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> valueService.findById(id));
        }
    }

    @Nested
    class FindByType {

        @Test
        void testFindByType_ExistingValues_ReturnValues() {
            // given
            int n_socialValues = 3;
            for (int i = 0; i < n_socialValues; i++) {
                var socialValues = createDummyValueDto();
                socialValues.setType(ValueType.SOCIAL);
                valueService.create(socialValues);
            }

            int n_economicValues = 4;
            for (int i = 0; i < n_economicValues; i++) {
                var economicValues = createDummyValueDto();
                economicValues.setType(ValueType.ECONOMIC);
                valueService.create(economicValues);
            }

            // when
            var socialValue = valueService.findAllByType(ValueType.SOCIAL);
            var economicValue = valueService.findAllByType(ValueType.ECONOMIC);

            // then
            assertThat(socialValue.size()).isEqualTo(n_socialValues);
            assertThat(economicValue.size()).isEqualTo(n_economicValues);
        }
    }

    @Nested
    class FindAll {

        @ParameterizedTest
        @ValueSource(ints = {0, 1, 2, 3})
        void testFindAll_CreatedValues_ReturnValue(int value) {
            // given
            for (int i = 0; i < value; i++) {
                var valueDto = createDummyValueDto();
                valueService.create(valueDto);
            }

            // when
            var values = valueService.findAll();

            // then
            assertThat(values.size()).isEqualTo(value);
        }
    }

    @Nested
    class FindAllByTypes {

        @Test
        void testFindAllTypes_ReturnAllTypes() {
            // given

            // when
            var valueTypes = valueService.findAllTypes();

            // then
            assertThat(valueTypes.size()).isEqualTo(ValueType.values().length);
            assertThat(valueTypes).isEqualTo(Arrays.asList(ValueType.values()));
        }
    }

    @Nested
    class Create {

        @Test
        void testCreate_CreatedValue_ReturnCreatedValue() {
            // given
            var valueDto = createDummyValueDto();

            // when
            var createdValue = valueService.create(valueDto);
            var retrievedValue = valueService.findById(createdValue.getId());

            // then
            assertThat(createdValue).isEqualTo(retrievedValue);
        }

        @Test
        void testCreate_ExistingId_ThrowEntityIdMustBeNullException() {
            // given
            var valueDto = createDummyValueDto();

            // when
            valueDto.setId(UUID.randomUUID());

            // then
            assertThatExceptionOfType(EntityIdMustBeNullException.class).isThrownBy(() -> valueService.create(valueDto));
        }
    }

    @Nested
    class Update {

        @Test
        void testUpdate_UpdatedValues_ReturnUpdatedValue() {
            // given
            var value = saveFullDummyValue();

            // when
            var newName = "new_name";
            value.setName(newName);
            valueService.update(toDto(value));
            var valueDto = valueService.findById(value.getId());

            // then
            assertThat(valueDto.getName()).isEqualTo(newName);
        }

        @Test
        void testUpdate_NonExistingId_ThrowEntityNotFoundException() {
            // given
            var valueDto = createDummyValueDto();
            valueDto.setId(UUID.randomUUID());

            // when

            // then
            assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> valueService.update(valueDto));
        }

        @Test
        void testUpdate_NullId_ThrowEntityIdRequiredException() {
            // given
            var valueDto = createDummyValueDto();

            // when

            // then
            assertThatExceptionOfType(EntityIdRequiredException.class).isThrownBy(() -> valueService.update(valueDto));
        }
    }

    @Nested
    class Delete {

        @Test
        void testDeleteById_DeleteValue_ReturnNoDValues() {
            // given
            var value = saveFullDummyValue();

            // when
            valueService.deleteById(value.getId());

            // then
            var values = valueService.findAll();
            assertThat(values.size()).isZero();
        }

        @Test
        void testDeleteById_NonExistingId_ThrowEntityNotFoundException() {
            // given
            var values = createDummyValue();
            values.setId(UUID.randomUUID());

            // when

            // then
            var id = values.getId();
            assertThatExceptionOfType(EntityNotFoundException.class).isThrownBy(() -> valueService.deleteById(id));
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
