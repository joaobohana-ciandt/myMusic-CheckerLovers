package com.ciandt.summit.bootcamp2022.domains.users;

import com.ciandt.summit.bootcamp2022.domains.playlists.Playlist;
import com.ciandt.summit.bootcamp2022.domains.userType.UserType;
import com.ciandt.summit.bootcamp2022.domains.users.dto.UserDTO;

public class User {

    public User() {
    }

    public User(String id, String name, Playlist playlist, UserType userType) {

    }

    public User(UserDTO userDTO) {

    }

    public UserDTO toDTO() {
        return null;
    }
}
