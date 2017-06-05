package com.sentilabs.okko.fixtures;

import com.sentilabs.okko.dto.user.UserDTO;

public class UserFixtures {

    public static UserDTO createUserDTO(Integer id) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName("name" + id);
        userDTO.setEmail("email" + id);
        userDTO.setPhone("phone" + id);
        return userDTO;
    }
}
