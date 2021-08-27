package com.evatool.common.exception;

import com.evatool.common.exception.prevent.http422.PropertyMustBeNullException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PropertyMustBeNullExceptionTest {

    @Test
    void testPropertyMustBeNullException() {
        // given
        var entityClass = "testClass";
        var entityProperty = "testProperty";

        // when
        var exception = new PropertyMustBeNullException(entityClass, entityProperty);

        // then
        assertThat(exception.getMessage()).contains(entityClass);
        assertThat(exception.getMessage()).contains(entityProperty);
    }
}
