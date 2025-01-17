package com.ciandt.summit.bootcamp2022.domains.songs.dtos;

import com.ciandt.summit.bootcamp2022.domains.artists.dtos.ArtistDTO;
import com.ciandt.summit.bootcamp2022.domains.songs.Song;

import java.io.Serializable;
import java.util.Objects;

public class SongDTO implements Serializable {

    private String id;
    private String name;
    private ArtistDTO artist;

    public SongDTO(String id, String name, ArtistDTO artist) {
        this.id = id;
        this.name = name;
        this.artist = artist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SongDTO songDTO = (SongDTO) o;
        return Objects.equals(id, songDTO.id) && Objects.equals(name, songDTO.name) && Objects.equals(artist, songDTO.artist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, artist);
    }

    public String getName() {
        return name;
    }

    public ArtistDTO getArtist() {
        return artist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(ArtistDTO artist) {
        this.artist = artist;
    }

    public Song toSong(){
        return new Song(id, name, artist.toArtist());
    }

    @Override
    public String toString() {
        return "{\"id\":\"" + id + '\"' +
                ",\"name\":\"" + name + '\"' +
                ",\"artist\":" + artist +
                '}';
    }
}
