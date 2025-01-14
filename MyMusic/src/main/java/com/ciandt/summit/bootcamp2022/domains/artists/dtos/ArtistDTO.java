package com.ciandt.summit.bootcamp2022.domains.artists.dtos;

import com.ciandt.summit.bootcamp2022.domains.artists.Artist;
import com.ciandt.summit.bootcamp2022.domains.songs.Song;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class ArtistDTO implements Serializable {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArtistDTO(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ArtistDTO artistDTO = (ArtistDTO) o;

        return Objects.equals(id, artistDTO.id) && Objects.equals(name, artistDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    public String getName() {
        return name;
    }

    public Artist toArtist() {
        return new Artist(id, name, new ArrayList<>());
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":\"" + id + '\"' +
                ",\"name\":\"" + name + '\"' +
                '}';
    }
}
