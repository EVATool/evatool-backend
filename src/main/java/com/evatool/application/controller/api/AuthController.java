package com.evatool.application.controller.api;

import com.evatool.application.dto.AuthRegisterRealmDto;
import com.evatool.application.dto.AuthRegisterUserDto;
import com.evatool.application.dto.AuthTokenDto;
import com.evatool.application.validator.Email;
import com.evatool.application.validator.Realm;
import com.evatool.application.validator.Username;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;

@Validated
public interface AuthController {

    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Login as user with password on realm")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request")})
    ResponseEntity<AuthTokenDto> login(@RequestParam @Username String username,
                                       @RequestParam @NotBlank String password,
                                       @RequestParam(required = false, defaultValue = "evatool-realm") @Realm String realm);

    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Refresh login with an existing token")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request")})
    ResponseEntity<AuthTokenDto> refreshLogin(@RequestParam @NotBlank String refreshToken,
                                              @RequestParam(required = false, defaultValue = "evatool-realm") @Realm String realm);

    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Register a new user")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request")})
    ResponseEntity<AuthRegisterUserDto> registerUser(@RequestParam @Username String username,
                                                     @RequestParam @Email String email,
                                                     @RequestParam @NotBlank String password);

    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Register a new realm")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request")})
    ResponseEntity<AuthRegisterRealmDto> registerRealm(@RequestParam @NotBlank String authAdminUsername,
                                                       @RequestParam @NotBlank String authAdminPassword,
                                                       @RequestParam @Realm String realm);
}
