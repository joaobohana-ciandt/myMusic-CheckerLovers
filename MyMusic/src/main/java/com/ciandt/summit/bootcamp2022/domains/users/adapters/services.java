package com.ciandt.summit.bootcamp2022.domains.users.adapters;

import com.ciandt.summit.bootcamp2022.domains.exceptions.users.UserNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.users.dto.UserDTO;
import com.ciandt.summit.bootcamp2022.domains.users.ports.interfaces.UserServicePort;

public class services implements UserServicePort {
    @Override
    public UserDTO findById(String id) throws UserNotFoundException {
        return null;
    }
}
