package com.evatool.common.exception.prevent;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PropertyCannotBeUpdatedExceptionTest {

    @Test
    void testPropertyCannotBeUpdatedException() {
        // given
        var entityClass = "testClass";
        var entityProperty = "testProperty";

        // when
        var exception = new PropertyCannotBeUpdatedException(entityClass, entityProperty);

        // then
        assertThat(exception.getMessage()).contains(entityClass);
        assertThat(exception.getMessage()).contains(entityProperty);
    }
}
