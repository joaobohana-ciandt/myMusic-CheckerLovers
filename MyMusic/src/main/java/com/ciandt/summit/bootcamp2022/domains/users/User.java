package com.ciandt.summit.bootcamp2022.domains.users;

import com.ciandt.summit.bootcamp2022.domains.playlists.Playlist;
import com.ciandt.summit.bootcamp2022.domains.userType.UserType;
import com.ciandt.summit.bootcamp2022.domains.users.dto.UserDTO;

public class User {
    private String id;
    private String name;
    private Playlist playlist;
    private UserType userType;

    public User() {
    }

    public User(String id, String name, Playlist playlist, UserType userType) {
        this.id = id;
        this.name = name;
        this.userType = userType;
        this.playlist = playlist;
    }

    public User(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.name = userDTO.getName();
        this.playlist = userDTO.getPlaylistDTO().toPlaylist();
        this.userType = userDTO.getUserTypeDTO().toUserType();
    }

    public UserDTO toDTO() {
        return new UserDTO(id, name, playlist.toPlaylistDTO(), userType.toDTO());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
