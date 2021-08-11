package com.evatool.application.service.impl;

import com.evatool.application.dto.UserDto;
import com.evatool.application.mapper.UserDtoMapper;
import com.evatool.application.service.TenancySentinel;
import com.evatool.application.service.api.UserService;
import com.evatool.common.exception.EntityNotFoundException;
import com.evatool.domain.entity.User;
import com.evatool.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends CrudServiceImpl<User, UserDto> implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository, UserDtoMapper mapper) {
        super(repository, mapper);
        this.repository = repository;
    }

    @Override
    public UserDto findByExternalUserId(String externalUserId) {
        logger.debug("Find By External User Id");
        var optional = repository.findByExternalUserId(externalUserId);
        if (optional.isEmpty()) {
            throw new EntityNotFoundException(User.class.getSimpleName(), externalUserId, "externalUserId");
        }
        var entity = optional.get();
        TenancySentinel.handleFind(entity);
        return baseMapper.toDto(entity);
    }
}
