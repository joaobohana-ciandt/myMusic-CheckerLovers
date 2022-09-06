package com.ciandt.summit.bootcamp2022.domains.playlists.dtos;

import com.ciandt.summit.bootcamp2022.domains.playlists.Playlist;
import com.ciandt.summit.bootcamp2022.domains.songs.Song;
import com.ciandt.summit.bootcamp2022.domains.songs.dtos.SongDTO;

import java.util.List;
import java.util.stream.Collectors;

public class PlaylistDTO {
    private String id;
    private List<SongDTO> songs;

    public PlaylistDTO(String id, List<SongDTO> songs) {
        this.id = id;
        this.songs = songs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<SongDTO> getSongs() {
        return songs;
    }

    public void setSongs(List<SongDTO> songs) {
        this.songs = songs;
    }

    public Playlist toPlaylist(){
        List<Song> toSongs = songs.stream().map(SongDTO::toSong).collect(Collectors.toList());
        return new Playlist(id, toSongs);
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + '\"' +
                ",\"songs\":\"" + songs +
                '}';
    }
}
