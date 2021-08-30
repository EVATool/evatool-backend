package com.evatool.application.controller.impl;

import com.evatool.application.controller.api.AuthController;
import com.evatool.application.dto.AuthRegisterRealmDto;
import com.evatool.application.dto.AuthRegisterUserDto;
import com.evatool.application.dto.AuthTokenDto;
import com.evatool.application.service.impl.AuthServiceImpl;
import com.evatool.common.util.UriUtil;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Auth API-Endpoint")
@RestController
public class AuthControllerImpl implements AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthControllerImpl.class);

    private final AuthServiceImpl authService;

    public AuthControllerImpl(AuthServiceImpl authService) {
        this.authService = authService;
    }

    @Override
    @PostMapping(UriUtil.AUTH_LOGIN)
    public ResponseEntity<AuthTokenDto> login(String username, String password, String realm) {
        var authTokenDto = authService.login(username, password, realm);
        return new ResponseEntity<>(authTokenDto, HttpStatus.OK);
    }

    @Override
    @PostMapping(UriUtil.AUTH_REFRESH_LOGIN)
    public ResponseEntity<AuthTokenDto> refreshLogin(String refreshToken, String realm) {
        var authTokenDto = authService.refreshLogin(refreshToken, realm);
        return new ResponseEntity<>(authTokenDto, HttpStatus.OK);
    }

    @Override
    @PostMapping(UriUtil.AUTH_REGISTER_USER)
    public ResponseEntity<AuthRegisterUserDto> registerUser(String username, String email, String password) {
        var authRegisterUserDto = authService.registerUser(username, email, password);
        return new ResponseEntity<>(authRegisterUserDto, HttpStatus.OK);
    }

    @Override
    @PostMapping(UriUtil.AUTH_REGISTER_REALM)
    public ResponseEntity<AuthRegisterRealmDto> registerRealm(String authAdminUsername, String authAdminPassword, String realm) {
        var authRegisterRealmDto = authService.registerRealm(authAdminUsername, authAdminPassword, realm);
        return new ResponseEntity<>(authRegisterRealmDto, HttpStatus.OK);
    }
}
