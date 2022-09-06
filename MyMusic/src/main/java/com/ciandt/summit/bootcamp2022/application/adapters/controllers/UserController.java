package com.ciandt.summit.bootcamp2022.application.adapters.controllers;

import com.ciandt.summit.bootcamp2022.application.adapters.controllers.docs.UserControllerDocs;
import com.ciandt.summit.bootcamp2022.domains.exceptions.users.UserNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.users.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public class UserController implements UserControllerDocs {
    @Override
    public ResponseEntity<UserDTO> findUserById(@PathVariable String userId) throws UserNotFoundException {
        return null;
    }
}
