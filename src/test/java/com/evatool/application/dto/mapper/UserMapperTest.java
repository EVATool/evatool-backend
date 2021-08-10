package com.evatool.application.dto.mapper;

import com.evatool.application.dto.UserDto;
import com.evatool.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMapperTest extends SuperMapperTest<User, UserDto, UserDtoMapper> {

    @Autowired
    private UserDtoMapper mapper;

}
