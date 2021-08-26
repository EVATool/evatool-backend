package com.evatool.application.controller.api;

import com.evatool.application.dto.AuthRegisterRealmDto;
import com.evatool.application.dto.AuthRegisterUserDto;
import com.evatool.application.dto.AuthTokenDto;
import com.evatool.application.validator.EmailConstraint;
import com.evatool.application.validator.UsernameRealmConstraint;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public interface AuthController {

    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Login as user with password on realm")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request")})
    ResponseEntity<AuthTokenDto> login(@Valid @RequestParam @UsernameRealmConstraint String username,
                                       @Valid @RequestParam @NotNull @NotBlank String password,
                                       @Valid @RequestParam @UsernameRealmConstraint String realm);

    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Refresh login with an existing token")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request")})
    ResponseEntity<AuthTokenDto> refreshLogin(@RequestParam String refreshToken,
                                              @Valid @RequestParam @UsernameRealmConstraint String realm);

    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Register a new user")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request")})
    ResponseEntity<AuthRegisterUserDto> registerUser(@Valid @RequestParam @UsernameRealmConstraint String username,
                                                     @Valid @RequestParam @EmailConstraint String email,
                                                     @Valid @RequestParam @NotNull @NotBlank String password);

    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Register a new realm")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request")})
    ResponseEntity<AuthRegisterRealmDto> registerRealm(@Valid @RequestParam @NotNull @NotBlank String authAdminUsername,
                                                       @Valid @RequestParam @NotNull @NotBlank String authAdminPassword,
                                                       @Valid @RequestParam @UsernameRealmConstraint String realm);
}
