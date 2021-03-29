package com.evatool.impact.domain.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static com.evatool.impact.common.TestDataGenerator.createDummyValue;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class SuperEntityUuidGeneratorTest {

    @Autowired
    private ImpactValueRepository impactValueRepository;

    @Test
    void testPersist_NullId_IdIsAutoGenerated() {
        // given
        var superEntity = createDummyValue();

        // when
       var savedSuperEntity = impactValueRepository.save(superEntity);

        // then
        assertThat(savedSuperEntity.getId()).isNotNull();
    }

    @Test
    void testPersist_NotNullId_PresetIdWasInserted() {
        // given
        var superEntity = createDummyValue();
        var id = UUID.randomUUID();
        superEntity.setId(id);

        // when
        var savedSuperEntity = impactValueRepository.save(superEntity);

        // then
        assertThat(savedSuperEntity.getId()).isNotNull();
        assertThat(savedSuperEntity.getId()).isEqualTo(id);
    }
}
