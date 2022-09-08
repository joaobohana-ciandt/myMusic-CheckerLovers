package com.ciandt.summit.bootcamp2022.domains.users.adapters.services;

import com.ciandt.summit.bootcamp2022.domains.exceptions.users.UserNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.users.dto.UserDTO;
import com.ciandt.summit.bootcamp2022.domains.users.ports.interfaces.UserServicePort;
import com.ciandt.summit.bootcamp2022.domains.users.ports.repositories.UserRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserServiceImp implements UserServicePort {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class.getSimpleName());
    private final UserRepositoryPort userRepositoryPort;

    public UserServiceImp(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }


    @Override
    public UserDTO findById(String id) throws UserNotFoundException {
        UserDTO user = this.userRepositoryPort.findById(id).toDTO();
        if (user == null) {
            throw new UserNotFoundException("User not found!");
        }
        return user;
    }
}
