package com.ciandt.summit.bootcamp2022.domains.songs.ports.interfaces;

import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.InvalidSongNameOrArtistNameException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.SongsNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.songs.dtos.SongResponseDTO;

public interface SongServicePort {
    SongResponseDTO findByNameOrArtistName(String name, int pageNumber)
            throws InvalidSongNameOrArtistNameException, SongsNotFoundException;

    SongResponseDTO findAllSongs(int pageNumber)
            throws SongsNotFoundException;
}
