package com.evatool.domain.repository;

import com.evatool.domain.entity.SuperEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
abstract class CrudRepositoryTest<T extends SuperEntity> extends DataTest {

    @Test
    void testFindById() {
        // given
        var entity = getPersistedEntity();

        // when
        var entityFound = getRepository().findById(entity.getId()).get();

        // then
        assertThat(entityFound).isEqualTo(entity);
    }

    @Test
    void testCreate() {
        // given
        var entity = getFloatingEntity();

        // when
        var entityCreated = (T) getRepository().save(entity);
        var entityFound = getRepository().findById(entityCreated.getId()).get();

        // then
        assertThat(entityFound).isEqualTo(entityCreated);
    }

    @Test
    void testUpdate() {
        // given
        var entity = getPersistedEntity();

        // when
        //changeEntity(entity);
        var entityUpdated = (T) getRepository().save(entity);
        var entityFound = getRepository().findById(entity.getId()).get();

        // then
        assertThat(entityFound).isEqualTo(entityUpdated);
    }

    @Test
    void testDeleteById() {
        // given
        var entity = getPersistedEntity();

        // when
        getRepository().delete(entity);
        var entityListFound = getRepository().findAll();

        // then
        assertThat(entityListFound).isEmpty();
    }
}
