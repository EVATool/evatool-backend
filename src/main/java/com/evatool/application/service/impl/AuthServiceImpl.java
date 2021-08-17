package com.evatool.application.service.impl;

import com.evatool.application.dto.AuthRegisterRealmDto;
import com.evatool.application.dto.AuthRegisterUserDto;
import com.evatool.application.dto.AuthTokenDto;
import com.evatool.application.service.api.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    public AuthTokenDto login(String username, String password, String realm) {
        var rest = new RestTemplate();
        return null;
    }

    @Override
    public AuthTokenDto refreshLogin(String refreshToken, String realm) {
        return null;
    }

    @Override
    public AuthRegisterUserDto registerUser(String username, String email, String password) {
        return null;
    }

    @Override
    public AuthRegisterRealmDto registerRealm(String authAdminUsername, String authAdminPassword, String realm) {
        return null;
    }
}
