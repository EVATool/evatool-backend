package com.evatool.application.service.api;

import com.evatool.application.dto.UserDto;

public interface UserService extends CrudService<UserDto> {

    UserDto findByExternalUserId(String externalUserId);

}
