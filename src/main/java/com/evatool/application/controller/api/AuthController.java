package com.evatool.application.controller.api;

import com.evatool.application.dto.AuthRegisterRealmDto;
import com.evatool.application.dto.AuthRegisterUserDto;
import com.evatool.application.dto.AuthTokenDto;
import com.evatool.application.validator.EmailConstraint;
import com.evatool.application.validator.RealmConstraint;
import com.evatool.application.validator.UsernameConstraint;
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
    ResponseEntity<AuthTokenDto> login(@RequestParam @UsernameConstraint String username,
                                       @RequestParam @NotBlank String password,
                                       @RequestParam @RealmConstraint String realm);

    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Refresh login with an existing token")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request")})
    ResponseEntity<AuthTokenDto> refreshLogin(@RequestParam @NotBlank String refreshToken,
                                              @RequestParam @RealmConstraint String realm);

    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Register a new user")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request")})
    ResponseEntity<AuthRegisterUserDto> registerUser(@RequestParam @UsernameConstraint String username,
                                                     @RequestParam @EmailConstraint String email,
                                                     @RequestParam String password);

    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    @ApiOperation(value = "Register a new realm")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request")})
    ResponseEntity<AuthRegisterRealmDto> registerRealm(@RequestParam @NotBlank String authAdminUsername,
                                                       @RequestParam @NotBlank String authAdminPassword,
                                                       @RequestParam @RealmConstraint String realm);
}
