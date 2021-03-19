package com.evatool.impact.domain.repository;

import com.evatool.impact.common.exception.NumericIdMustBeNullException;
import com.evatool.impact.domain.entity.NumericId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.evatool.impact.common.TestDataGenerator.createDummyNumericId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NumericIdRepositoryTest {

    @Autowired
    NumericIdRepository numericIdRepository;

    @BeforeEach
    @AfterAll
    private void clearDatabase() {
        numericIdRepository.deleteAll();
    }

    private NumericId saveFullDummyNumericId() {
        return numericIdRepository.save(new NumericId());
    }

    @Test
    void testSave_SaveNewNumericId_NumericIdGenerated() {
        // given
        var numericId = saveFullDummyNumericId();

        // when

        // then
        assertThat(numericId.getNumericId()).isNotNull();
    }

    @Test
    void testSave_SaveNewNumericId_ReadableIdGenerated() {
        // given
        var numericId = saveFullDummyNumericId();

        // when

        // then
        System.out.println(numericId);
        assertThat(numericId._getReadableId()).isNotNull();
    }

    @Test
    void testFindById_SavedNumericId_NumericIdAvailable() {
        // given
        var numericId = saveFullDummyNumericId();

        // when

        // then
        assertThat(numericId.getNumericId()).isNotNull();
    }

    @Test
    void testFindById_SavedNumericId_ReadableIdAvailable() {
        // given
        var numericId = saveFullDummyNumericId();

        // when

        // then
        assertThat(numericId._getReadableId()).isNotNull();
    }

    @Test
    void testSetNumericId_UnsavedNumericId_CannotSetNumericId() {
        // given
        var numericId = createDummyNumericId();

        // when
        numericId.setNumericId(1);

        // then
        assertThatExceptionOfType(NumericIdMustBeNullException.class).isThrownBy(() -> numericIdRepository.save(numericId));
    }

    @Test
    void testSetNumericId_SavedNumericId_CannotChangeNumericId() {
        // given
        var numericId = saveFullDummyNumericId();

        // when

        // then
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> numericId.setNumericId(1));
    }
}
