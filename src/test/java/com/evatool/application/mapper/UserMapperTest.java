package com.evatool.application.mapper;

import com.evatool.application.dto.UserDto;
import com.evatool.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserMapperTest extends SuperMapperTest<User, UserDto, UserMapper> {

    @Autowired
    private UserMapper mapper;

}
