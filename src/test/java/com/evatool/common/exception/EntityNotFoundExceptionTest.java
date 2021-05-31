package com.evatool.common.exception;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class EntityNotFoundExceptionTest {

    @Test
    void testEntityNotFoundException() {
        // given
        var entityClass = "testClass";
        var id = UUID.randomUUID();

        // when
        var exception = new EntityNotFoundException(entityClass, id);

        // then
        assertThat(exception.getMessage()).contains(entityClass);
        assertThat(exception.getMessage()).contains(id.toString());
    }
}
