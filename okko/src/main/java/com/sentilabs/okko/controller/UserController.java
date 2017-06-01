package com.sentilabs.okko.controller;

import com.sentilabs.okko.dto.UserDTO;
import com.sentilabs.okko.entity.UserEntity;
import com.sentilabs.okko.mapper.Mapper;
import com.sentilabs.okko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Callable;

@RestController
@RequestMapping(path = ControllerConfiguration.Users.USERS_FULL_BASE_URL)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(path = "/{userId}")
    public Callable<UserDTO> getUserById(@PathVariable String userId) {
        Callable<UserDTO> deferredResult = () -> {
            UserEntity userEntity = userService.findUserById(userId);
            return Mapper.map(userEntity);
        };

        return deferredResult;
    }
}
