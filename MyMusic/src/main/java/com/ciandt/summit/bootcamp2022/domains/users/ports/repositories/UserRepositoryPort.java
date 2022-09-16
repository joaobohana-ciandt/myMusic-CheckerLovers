package com.ciandt.summit.bootcamp2022.domains.users.ports.repositories;

import com.ciandt.summit.bootcamp2022.domains.exceptions.users.UserNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.users.User;
import com.ciandt.summit.bootcamp2022.domains.users.dto.UserDTO;
import org.springframework.stereotype.Repository;

public interface UserRepositoryPort {

    User findById(String id) throws UserNotFoundException;
}
