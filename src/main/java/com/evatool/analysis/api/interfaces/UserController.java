package com.evatool.analysis.api.interfaces;

import com.evatool.analysis.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;


@Api("API-endpoint for user")
public interface UserController {

    @GetMapping("/users")
    @ApiOperation(value = "This method returns a list of all user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "All entities returned")})
    public List<EntityModel<UserDTO>> getUserList();

    @GetMapping("/users/{id}")
    @ApiOperation(value = "This method returns an Optional of a user by his ID ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The entity was found"),
            @ApiResponse(code = 400, message = "The id was invalid"),
            @ApiResponse(code = 404, message = "The entity was not found")})
    public EntityModel<UserDTO> getUserById(@PathVariable UUID id);

    @PostMapping("/users")
    @ApiOperation(value = "This method add a user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The entity is inserted"),
            @ApiResponse(code = 400, message = "The entity is invalid"),
            @ApiResponse(code = 404, message = "The entity is not found")})
    public EntityModel<UserDTO> addUser(@RequestBody UserDTO userDTO);

    @PutMapping("/users/{id}")
    @ApiOperation(value = "This method updated an user by his id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The entity is deleted"),
            @ApiResponse(code = 404, message = "The entity is not found")})
    public EntityModel<UserDTO> updateUser(@RequestBody UserDTO userDTO);

    @DeleteMapping("/users/{id}")
    @ApiOperation(value = "This method delete an user by his id ")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The entity is updated"),
            @ApiResponse(code = 400, message = "The entity is invalid"),
            @ApiResponse(code = 404, message = "The entity is not found")})
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id);
}
