package com.evatool.analysis.services;

import com.evatool.analysis.dto.UserDTO;
import com.evatool.analysis.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDTOService {
    Logger logger = LoggerFactory.getLogger(UserDTOService.class);

    @Autowired
    private UserMapper userMapper;

    public List<UserDTO> findAll(List<User> userDTOList) {
        logger.info("findAll");
        return userMapper.map(userDTOList);
    }

    public UserDTO findById(User user) {
        logger.debug("findId [{}]",user);
        return userMapper.map(user);
    }

    public User create(UserDTO userDTO) {
        logger.debug("create [{}]",userDTO);
        User user = new User();
        user.setUserEmail(userDTO.getEmail());
        user.setUserPassword(userDTO.getPassword());
        user.setUserName(userDTO.getUserName());
        return user;
    }
}
