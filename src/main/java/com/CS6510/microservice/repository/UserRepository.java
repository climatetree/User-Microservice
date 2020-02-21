package com.CS6510.microservice.repository;

import com.CS6510.microservice.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {


}
