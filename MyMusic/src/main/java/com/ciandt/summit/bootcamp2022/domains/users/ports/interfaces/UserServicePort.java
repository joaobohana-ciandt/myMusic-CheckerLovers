package com.ciandt.summit.bootcamp2022.domains.users.ports.interfaces;

import com.ciandt.summit.bootcamp2022.domains.exceptions.users.UserNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.users.dto.UserDTO;


public interface UserServicePort {
    UserDTO findById(String id)
            throws UserNotFoundException;
}
