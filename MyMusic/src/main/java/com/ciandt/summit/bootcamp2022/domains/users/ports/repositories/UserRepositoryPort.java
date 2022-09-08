package com.ciandt.summit.bootcamp2022.domains.users.ports.repositories;

import com.ciandt.summit.bootcamp2022.domains.exceptions.users.UserNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.users.User;
import com.ciandt.summit.bootcamp2022.domains.users.dto.UserDTO;

public interface UserRepositoryPort {

    UserDTO findById(String id) throws UserNotFoundException;
}
