package com.ciandt.summit.bootcamp2022.domains.users.ports.repositories;

import com.ciandt.summit.bootcamp2022.domains.exceptions.users.UserNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.users.User;

public interface UserRepositoryPort {

    User findById(String id) throws UserNotFoundException;
}
