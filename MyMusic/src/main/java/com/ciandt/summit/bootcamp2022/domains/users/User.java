package com.ciandt.summit.bootcamp2022.domains.users;

import com.ciandt.summit.bootcamp2022.domains.playlists.Playlist;
import com.ciandt.summit.bootcamp2022.domains.users.dto.UserDTO;

public class User {

    public User() {
    }

    // TODO: adicionar o UserType userType como param nesse const.
    public User(String id, String name, Playlist playlist) {

    }

    public User(UserDTO userDTO) {

    }

    public UserDTO toDTO() {
        return null;
    }
}
