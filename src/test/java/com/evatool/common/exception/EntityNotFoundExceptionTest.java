package com.evatool.common.exception;

import com.evatool.domain.entity.Analysis;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class EntityNotFoundExceptionTest {

    @Test
    void testEntityNotFoundException() {
        // given
        var exception = new EntityNotFoundException(Analysis.class.getSimpleName(), UUID.randomUUID());

        // when


        // then

    }
}
