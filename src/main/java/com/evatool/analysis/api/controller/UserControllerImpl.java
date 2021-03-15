package com.evatool.analysis.api.controller;

import com.evatool.analysis.api.interfaces.UserController;
import com.evatool.analysis.dto.UserDTO;
import com.evatool.analysis.events.AnalysisEventPublisher;
import com.evatool.analysis.model.Analysis;
import com.evatool.analysis.model.User;
import com.evatool.analysis.repository.UserRepository;
import com.evatool.analysis.services.UserDTOService;
import com.evatool.global.event.analysis.AnalysisUpdatedEvent;
import com.evatool.global.event.user.UserCreatedEvent;
import com.evatool.global.event.user.UserDeletedEvent;
import com.evatool.analysis.error.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserControllerImpl implements UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDTOService userDTOService;

    @Autowired
    private AnalysisEventPublisher userEventPublisher;

    Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);


    @Override
    public List<EntityModel<UserDTO>> getUserList() {
        logger.info("[GET] /user");
        List<User> userList = userRepository.findAll();
        if (userList.isEmpty()){
            return Arrays.asList();
        }
        return generateLinks(userDTOService.findAll(userList));
    }

    @Override
    public EntityModel<UserDTO> getUserById(UUID id) {
        logger.info("[GET] /user/{id}");
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()){
            throw new EntityNotFoundException(Analysis.class, id);
        }
        return generateLinks(userDTOService.findById(userOptional.get()));
    }

    @Override
    public EntityModel<UserDTO> addUser(UserDTO userDTO) {
        logger.info("[POST] /addUser");
        User user = userRepository.save(userDTOService.create(userDTO));

        userEventPublisher.publishEvent(new UserCreatedEvent(user.toString()));
        return getUserById(user.getUserId());
    }

    @Override
    public EntityModel<UserDTO> updateUser(UserDTO userDTO) {
        logger.info("[PUT] /user");
        Optional<User> userOptional = userRepository.findById(userDTO.getRootEntityID());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setUserName(userDTO.getUserName());
            user.setUserEmail(userDTO.getEmail());
            user.setUserPassword(userDTO.getPassword());
            userEventPublisher.publishEvent(new AnalysisUpdatedEvent(user.toString()));
        }
        return getUserById(userDTO.getRootEntityID());
    }

    @Override
    public ResponseEntity<Void> deleteUser(UUID id) {
        logger.info("[DELETE] /user/{id}");
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()){
            throw new EntityNotFoundException(User.class, id);
        }
        User user = userOptional.get();
        userRepository.deleteById(id);
        userEventPublisher.publishEvent(new UserDeletedEvent(user.toString()));
        return ResponseEntity.ok().build();
    }

    private EntityModel<UserDTO> generateLinks(UserDTO userDTO){
        EntityModel<UserDTO> userDTOEntityModel = EntityModel.of(userDTO);
        userDTOEntityModel.add(linkTo(methodOn(UserController.class).getUserById(userDTO.getRootEntityID())).withSelfRel());
        userDTOEntityModel.add(linkTo(methodOn(UserController.class).deleteUser(userDTO.getRootEntityID())).withRel("Deleted"));
        userDTOEntityModel.add(linkTo(methodOn(UserController.class).updateUser(userDTO)).withRel("update"));
        return userDTOEntityModel;
    }

    private List<EntityModel<UserDTO>> generateLinks(List<UserDTO> userDTOList){
        List<EntityModel<UserDTO>> returnList = new ArrayList<>();
        userDTOList.stream().forEach(e -> returnList.add(generateLinks(e)));
        return returnList;

    }
}
