package com.sentilabs.okko.controller;

import com.sentilabs.okko.dto.user.UserDTO;
import com.sentilabs.okko.entity.user.UserEntity;
import com.sentilabs.okko.mapper.EntityDTOMapper;
import com.sentilabs.okko.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

@RestController
@RequestMapping(path = ControllerConfiguration.Users.USERS_FULL_BASE_URL)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(
            method = RequestMethod.GET
    )
    public Callable<List<UserDTO>> getUsers() {
        Callable<List<UserDTO>> deferredResult = () -> {
            List<UserEntity> userEntities = userService.getUsers();
            return EntityDTOMapper.mapUserEntitieToDTOs(userEntities);
        };

        return deferredResult;
    }

    @RequestMapping(
            path = "/{" + ControllerConfiguration.Users.UserById.PATH_PARAM_USER_ID + "}",
            method = RequestMethod.GET
    )
    public Callable<UserDTO> getUserByName(
            @PathVariable(ControllerConfiguration.Users.UserById.PATH_PARAM_USER_ID) String userId
    ) {
        Callable<UserDTO> deferredResult = () -> {
            UserEntity userEntity = userService.findUserByName(userId);
            return EntityDTOMapper.mapUserEntityToDTO(userEntity);
        };

        return deferredResult;
    }

    @RequestMapping(
            method = RequestMethod.GET,
            params = ControllerConfiguration.Users.SEARCH_BY_EMAIL_REQ_PARAM
    )
    public Callable<UserDTO> getUserByEmail(
            @RequestParam(ControllerConfiguration.Users.SEARCH_BY_EMAIL_REQ_PARAM) String userName
    ) {
        Callable<UserDTO> deferredResult = () -> {
            UserEntity userEntity = userService.findUserByEmail(userName);
            return EntityDTOMapper.mapUserEntityToDTO(userEntity);
        };

        return deferredResult;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Callable<Void> createUsers(
            @RequestBody UserDTO[] userDTOs
    ) {
        Callable<Void> deferredResult = () -> {
            userService.createUsers(Arrays.asList(userDTOs));
            return null;
        };

        return deferredResult;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public Callable<Void> updateUser(
            @RequestBody UserDTO[] userDTOs
    ) {
        Callable<Void> deferredResult = () -> {
            userService.updateUsers(Arrays.asList(userDTOs));
            return null;
        };

        return deferredResult;
    }

    @RequestMapping(
            path = "/{" + ControllerConfiguration.Users.UserById.PATH_PARAM_USER_ID + "}",
            method = RequestMethod.DELETE
    )
    public Callable<Void> deleteUserByName(
            @PathVariable(ControllerConfiguration.Users.UserById.PATH_PARAM_USER_ID) String userId
    ) {
        Callable<Void> deferredResult = () -> {
            userService.deleteUserByName(userId);
            return null;
        };

        return deferredResult;
    }


}
