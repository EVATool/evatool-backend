package com.evatool.application.service;

import com.evatool.application.dto.UserDto;
import com.evatool.application.mapper.UserMapper;
import com.evatool.application.service.impl.UserServiceImpl;
import com.evatool.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

class UserServiceTest extends CrudServiceTest<User, UserDto> {

    @Autowired
    private UserServiceImpl service;

}
