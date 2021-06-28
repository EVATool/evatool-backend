package com.evatool.application.controller.impl;

import com.evatool.common.util.UriUtil;
import com.evatool.application.controller.api.UserController;
import com.evatool.application.dto.UserDto;
import com.evatool.application.service.impl.UserServiceImpl;
import com.evatool.domain.entity.User;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(tags = "User API-Endpoint")
@RestController
public class UserControllerImpl extends CrudControllerImpl<User, UserDto> implements UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);

    private final UserServiceImpl service;

    protected UserControllerImpl(UserServiceImpl service) {
        super(service);
        this.service = service;
    }

    @Override
    public ResponseEntity<EntityModel<UserDto>> findByExternalUserId(String externalUserId) {
        var dtoFound = service.findByExternalUserId(externalUserId);
        return new ResponseEntity<>(withLinks(dtoFound), HttpStatus.OK);
    }

    @Override
    @GetMapping(UriUtil.USERS_ID)
    public ResponseEntity<EntityModel<UserDto>> findById(UUID id) {
        return super.findById(id);
    }

    @Override
    @PostMapping(UriUtil.USERS)
    public ResponseEntity<EntityModel<UserDto>> create(UserDto dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping(UriUtil.USERS)
    public ResponseEntity<EntityModel<UserDto>> update(UserDto dto) {
        return super.update(dto);
    }

    @Override
    @DeleteMapping(UriUtil.USERS_ID)
    public ResponseEntity<Void> deleteById(UUID id) {
        return super.deleteById(id);
    }
}
