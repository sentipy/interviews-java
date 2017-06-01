package com.sentilabs.okko.mapper;

import com.sentilabs.okko.dto.UserDTO;
import com.sentilabs.okko.entity.UserEntity;

public class Mapper {

    public static UserDTO map(UserEntity userEntity) {
        UserDTO userDTO = new UserDTO();
        return userDTO;
    }

    public static UserEntity map(UserDTO userDTO) {
        UserEntity userEntity = new UserEntity();
        return userEntity;
    }
}
