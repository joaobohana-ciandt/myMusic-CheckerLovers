package com.ciandt.summit.bootcamp2022.domains.users.dto;

import com.ciandt.summit.bootcamp2022.domains.playlists.dtos.PlaylistDTO;
import com.ciandt.summit.bootcamp2022.domains.userType.dto.UserTypeDTO;

public class UserDTO {
    private String id;
    private String name;
    private PlaylistDTO playlistDTO;
    private UserTypeDTO userTypeDTO;

    public UserDTO(String id, String name, PlaylistDTO playlistDTO, UserTypeDTO userTypeDTO) {
        this.id = id;
        this.name = name;
        this.playlistDTO = playlistDTO;
        this.userTypeDTO = userTypeDTO;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PlaylistDTO getPlaylistDTO() {
        return playlistDTO;
    }

    public UserTypeDTO getUserTypeDTO() {
        return userTypeDTO;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlaylistDTO(PlaylistDTO playlistDTO) {
        this.playlistDTO = playlistDTO;
    }

    public void setUserTypeDTO(UserTypeDTO userTypeDTO) {
        this.userTypeDTO = userTypeDTO;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + '\"' +
                ",\"name\":\"" + name + '\"' +
                ",\"playlistDTO\":" + playlistDTO +
                ",\"userTypeDTO\":" + userTypeDTO +
                '}';
    }
}
