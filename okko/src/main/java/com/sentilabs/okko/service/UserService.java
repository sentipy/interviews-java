package com.sentilabs.okko.service;

import com.sentilabs.okko.entity.UserEntity;
import com.sentilabs.okko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserEntity findUserById(String userId) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userId);
        Example<UserEntity> userEntityExample = Example.of(userEntity);
        return userRepository.findOne(userEntityExample);
    }
}
