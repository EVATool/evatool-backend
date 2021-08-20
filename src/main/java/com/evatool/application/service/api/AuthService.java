package com.evatool.application.service.api;

import com.evatool.application.dto.AuthRegisterRealmDto;
import com.evatool.application.dto.AuthRegisterUserDto;
import com.evatool.application.dto.AuthTokenDto;

public interface AuthService { // TODO add validation to username and realm (allow alphanumeric and dash)

    AuthTokenDto login(String username, String password, String realm);

    AuthTokenDto refreshLogin(String refreshToken, String realm);

    AuthRegisterUserDto registerUser(String username, String email, String password);

    AuthRegisterRealmDto registerRealm(String authAdminUsername, String authAdminPassword, String realm);

}
