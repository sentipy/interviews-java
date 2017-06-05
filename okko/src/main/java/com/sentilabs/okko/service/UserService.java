package com.sentilabs.okko.service;

import com.sentilabs.okko.dto.user.UserDTO;
import com.sentilabs.okko.entity.user.UserEntity;
import com.sentilabs.okko.mapper.EntityDTOMapper;
import com.sentilabs.okko.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public List<UserEntity> getUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public UserEntity findUserByName(String name) {
        return userRepository.findById(name).orElse(null);
    }

    @Transactional(readOnly = true)
    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void createUsers(List<UserDTO> userDTOs) {
        List<UserEntity> userEntities = EntityDTOMapper.mapUserDTOsToEntities(userDTOs);
        userRepository.insert(userEntities);
    }

    @Transactional
    public void updateUsers(List<UserDTO> userDTOs) {
        List<UserEntity> userEntities = EntityDTOMapper.mapUserDTOsToEntities(userDTOs);
        userRepository.saveAll(userEntities);
    }

    @Transactional
    public void deleteUserByName(String userName) {
        userRepository.deleteById(userName);
    }

}
