package com.evatool.application.controller.api;

import com.evatool.application.dto.UserDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

public interface UserController extends CrudController<UserDto> {

    @GetMapping(produces = {"application/json"})
    @ApiOperation(value = "Find entity by requirement id")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad Request")})
    ResponseEntity<Iterable<EntityModel<UserDto>>> findByExternalUserId(@Valid @RequestParam String externalUserId);

}
