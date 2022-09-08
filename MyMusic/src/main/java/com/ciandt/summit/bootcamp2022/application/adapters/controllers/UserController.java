package com.ciandt.summit.bootcamp2022.application.adapters.controllers;

import com.ciandt.summit.bootcamp2022.application.adapters.controllers.docs.UserControllerDocs;
import com.ciandt.summit.bootcamp2022.domains.exceptions.users.UserNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.songs.ports.interfaces.SongServicePort;
import com.ciandt.summit.bootcamp2022.domains.users.dto.UserDTO;
import com.ciandt.summit.bootcamp2022.domains.users.ports.interfaces.UserServicePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public class UserController implements UserControllerDocs {

    private static final Logger logger = LoggerFactory.getLogger(SongsController.class.getSimpleName());

    @Autowired
    private UserServicePort userServicePort;

    @Override
    public ResponseEntity<UserDTO> findUserById(@PathVariable String userId) throws UserNotFoundException {
        return null;
    }
}
