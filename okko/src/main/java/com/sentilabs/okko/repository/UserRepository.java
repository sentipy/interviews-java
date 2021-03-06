package com.sentilabs.okko.repository;

import com.sentilabs.okko.entity.user.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String> {

    UserEntity findByEmail(String email);
}
