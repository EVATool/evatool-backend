package com.evatool.common.exception;

import com.evatool.common.exception.prevent.http422.PropertyIsInvalidException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PropertyIsInvalidExceptionTest {

    @Test
    void testPropertyIsInvalidException() {
        // given
        var entityClass = "testClass";
        var entityProperty = "testProperty";

        // when
        var exception = new PropertyIsInvalidException(String.format("Property '%s' of '%s' is invalid", entityProperty, entityClass));

        // then
        assertThat(exception.getMessage()).contains(entityClass);
        assertThat(exception.getMessage()).contains(entityProperty);
    }
}
