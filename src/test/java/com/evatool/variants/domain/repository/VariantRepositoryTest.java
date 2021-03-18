package com.evatool.variants.domain.repository;

import com.evatool.variants.domain.entities.Variant;
import com.evatool.variants.domain.repositories.VariantRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class VariantRepositoryTest {

    @Autowired
    private VariantRepository variantRepository;

    @Test
    void testFindById_InsertedVariant_ReturnVariant() {
        // given
        Variant variant = new Variant();
        variantRepository.save(variant);

        // when
        Variant found = variantRepository.findById(variant.getId()).orElse(null);

        // then
        assert found != null;
        assertThat(found.getId()).isEqualTo(variant.getId());
    }

    @Test
    void testSave_InsertedVariant_IdIsNotNull() {
        // given
        Variant variant = new Variant();

        // when
        variantRepository.save(variant);

        // then
        assertThat(variant.getId()).isNotNull();
    }

    @Test
    void testSave_UpdatedVariant_ReturnUpdatedVariant() {
        // given
        Variant variant = new Variant();
        variantRepository.save(variant);
        String newTitle = "new_title";

        // when
        variant.setTitle(newTitle);
        variantRepository.save(variant);
        Variant changedVariant = variantRepository.findById(variant.getId()).orElse(null);

        // then
        assert changedVariant != null;
        assertThat(changedVariant.getTitle()).isEqualTo(newTitle);
    }

    @Test
    void testDelete_DeletedVariant_ReturnNull() {
        // given
        Variant variant = new Variant();
        variantRepository.save(variant);

        // when
        variantRepository.delete(variant);
        Variant found = variantRepository.findById(variant.getId()).orElse(null);

        // then
        assertThat(found).isNull();
    }

    @Nested
    class SuperEntityUuidGeneratorTest {

        @Test
        void testPersist_NullId_Allow() {
            // given
            Variant superEntity = new Variant();

            // when
            Variant savedSuperEntity = variantRepository.save(superEntity);

            // then
            assertThat(savedSuperEntity.getId()).isNotNull();
        }

        @Test
        void testPersist_NotNullId_Allow() {
            // given
            Variant superEntity = new Variant();
            UUID id = UUID.randomUUID();
            superEntity.setId(id);

            // when
            Variant savedSuperEntity = variantRepository.save(superEntity);

            // then
            assertThat(savedSuperEntity.getId()).isNotNull();
//            assertThat(savedSuperEntity.getId()).isEqualTo(id);
        }
    }


}