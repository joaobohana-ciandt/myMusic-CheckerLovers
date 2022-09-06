package com.ciandt.summit.bootcamp2022.infra.adapters.repositories;

import com.ciandt.summit.bootcamp2022.domains.exceptions.users.UserNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.users.User;
import com.ciandt.summit.bootcamp2022.domains.users.ports.repositories.UserRepositoryPort;

public class UserRepository implements UserRepositoryPort {
    @Override
    public User findById(String id) throws UserNotFoundException {
        return null;
    }
}
