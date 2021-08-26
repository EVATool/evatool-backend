package com.evatool.common.validation;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.evatool.common.validation.UsernameRealmValidation.validateUsernameOrRealm;
import static org.assertj.core.api.Assertions.assertThat;

class UsernameRealmValidationTest {

    @ParameterizedTest
    @ValueSource(strings = {"evatool-realm", "testuser", "admin", "a.b-c_d"})
    void testValidateUsernameOrRealm_Valid_OK(String value) {
        // given

        // when
        var error = validateUsernameOrRealm(value);

        // then
        assertThat(error).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "null", "realm%", "user name"})
    void testValidateUsernameOrRealm_Invalid_Error(String value) {
        // given
        value = value.equals("null") ? null : value;

        // when
        var error = validateUsernameOrRealm(value);

        // then
        assertThat(error).isNotNull();
    }
}
