package com.evatool.common.exception.handle;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorMessageTest {

    @Test
    void testErrorMessage() {
        // given
        var exception = new Exception("Test");

        // when
        var errorMessage = new ErrorMessage(exception, "/path", HttpStatus.OK);

        // then
        assertThat(errorMessage.getTimestamp().getTime()).isLessThanOrEqualTo(new Date().getTime());
        assertThat(errorMessage.getHttpStatusCode()).isEqualTo(200);
        assertThat(errorMessage.getHttpStatus()).isEqualTo("OK");
        assertThat(errorMessage.getTrace()).contains("java.lang.Exception: Test");
        assertThat(errorMessage.getMessage()).isEqualTo("Test");
        assertThat(errorMessage.getPath()).isEqualTo("/path");
    }
}
