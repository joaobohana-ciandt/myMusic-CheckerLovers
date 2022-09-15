package com.ciandt.summit.bootcamp2022.domains.users.adapters.services;

import com.ciandt.summit.bootcamp2022.domains.exceptions.users.UserNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.users.User;
import com.ciandt.summit.bootcamp2022.domains.users.dto.UserDTO;
import com.ciandt.summit.bootcamp2022.domains.users.ports.interfaces.UserServicePort;
import com.ciandt.summit.bootcamp2022.domains.users.ports.repositories.UserRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;


public class UserServiceImp implements UserServicePort {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class.getSimpleName());
    private final UserRepositoryPort userRepositoryPort;

    public UserServiceImp(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }


    @Override
    @Cacheable(value = "users", key = "#id")
    public UserDTO findById(String id) throws UserNotFoundException {
        logger.info("Getting user with id " + id);
        User result = this.userRepositoryPort.findById(id);
        return result.toDTO();
    }
}
