package com.evatool.impact.domain.repository;

import com.evatool.impact.domain.entity.NumericId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NumericIdRepositoryTest {

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
    void testSave_NumericIdGenerated() {
        // given
        var numericId = saveFullDummyNumericId();

        // when

        // then
    }
}
