package com.evatool.application.dto.mapper;

import com.evatool.application.dto.UserDto;
import com.evatool.domain.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserDtoMapper extends SuperDtoMapper<User, UserDto> {

    private static final Logger logger = LoggerFactory.getLogger(UserDtoMapper.class);

    @Override
    public UserDto toDto(User entity) {
        logger.debug("To Dto");
        var dto = new UserDto(
                entity.getExternalUserId()
        );
        super.amendToDto(entity, dto);
        return dto;
    }

    @Override
    public User fromDto(UserDto dto) {
        logger.debug("From Dto");
        var entity = new User(
                dto.getExternalUserId()
        );
        super.amendFromDto(entity, dto);
        return entity;
    }
}
