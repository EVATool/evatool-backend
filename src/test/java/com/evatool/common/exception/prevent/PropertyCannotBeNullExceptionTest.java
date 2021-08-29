package com.evatool.common.exception.prevent;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PropertyCannotBeNullExceptionTest {

    @Test
    void testPropertyCannotBeNullException() {
        // given
        var entityClass = "testClass";
        var entityProperty = "testProperty";

        // when
        var exception = new PropertyCannotBeNullException(entityClass, entityProperty);

        // then
        assertThat(exception.getMessage()).contains(entityClass);
        assertThat(exception.getMessage()).contains(entityProperty);
    }
}
