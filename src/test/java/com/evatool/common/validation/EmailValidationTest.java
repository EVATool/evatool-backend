package com.evatool.common.validation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.evatool.common.validation.EmailValidation.validateEmail;
import static org.assertj.core.api.Assertions.assertThat;

class EmailValidationTest {

    @ParameterizedTest
    @ValueSource(strings = {"a@b", "3tz784.q34dcnz7899w3c45hn78.d34rt9bgn67@8oq3c4nz57"})
    void testValidateEmail_ValidEmail_OK(String email) {
        // given

        // when
        var error = validateEmail(email);

        // then
        assertThat(error).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "null", "a@b@c", " ", "%6", "a.ö@gmail.com"})
    void testValidateEmail_InvalidEmail_Error(String email) {
        // given
        email = email.equals("null") ? null : email;

        // when
        var error = validateEmail(email);

        // then
        assertThat(error).isNotNull();
    }
}
