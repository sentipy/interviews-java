package com.sentilabs.okko.mapper;

import com.sentilabs.okko.dto.user.UserDTO;
import com.sentilabs.okko.entity.user.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class EntityDTOMapper {

    public static UserDTO mapUserEntityToDTO(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setName(userEntity.getName());
        userDTO.setEmail(userEntity.getEmail());
        userDTO.setPhone(userEntity.getPhone());
        userDTO.setLinkedAccounts(userEntity.getLinkedAccounts());
        return userDTO;
    }

    public static List<UserDTO> mapUserEntitieToDTOs(List<UserEntity> userEntities) {
        if (userEntities == null) {
            return null;
        }
        List<UserDTO> result = new ArrayList<>(userEntities.size());
        for (UserEntity userEntity : userEntities) {
            result.add(mapUserEntityToDTO(userEntity));
        }
        return result;
    }

    public static UserEntity mapUserDTOToEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDTO.getName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPhone(userDTO.getPhone());
        userEntity.setLinkedAccounts(userDTO.getLinkedAccounts());
        return userEntity;
    }

    public static List<UserEntity> mapUserDTOsToEntities(List<UserDTO> userDTOs) {
        if (userDTOs == null) {
            return null;
        }
        List<UserEntity> result = new ArrayList<>();
        for (UserDTO userDTO : userDTOs) {
            result.add(mapUserDTOToEntity(userDTO));
        }
        return result;
    }
}
