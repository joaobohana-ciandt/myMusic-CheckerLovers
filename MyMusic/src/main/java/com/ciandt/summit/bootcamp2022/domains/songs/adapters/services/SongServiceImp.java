package com.ciandt.summit.bootcamp2022.domains.songs.adapters.services;

import com.ciandt.summit.bootcamp2022.application.adapters.controllers.PlaylistController;
import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.InvalidSongNameOrArtistNameException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.SongsNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.songs.Song;
import com.ciandt.summit.bootcamp2022.domains.songs.SongsPaginated;
import com.ciandt.summit.bootcamp2022.domains.songs.dtos.SongDTO;
import com.ciandt.summit.bootcamp2022.domains.songs.dtos.SongResponseDTO;
import com.ciandt.summit.bootcamp2022.domains.songs.ports.interfaces.SongServicePort;
import com.ciandt.summit.bootcamp2022.domains.songs.ports.repositories.SongRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

public class SongServiceImp implements SongServicePort {

    private static final Logger logger = LoggerFactory.getLogger(PlaylistController.class.getSimpleName());
    private final SongRepositoryPort songRepositoryPort;

    public SongServiceImp(SongRepositoryPort songRepositoryPort) {
        this.songRepositoryPort = songRepositoryPort;
    }

    @Override
    public SongResponseDTO findByNameOrArtistName(String name, int pageNumber)
            throws InvalidSongNameOrArtistNameException, SongsNotFoundException {

        if(name == null || name.isBlank() || name.length() < 2){
            logger.error("Recebe o erro Filter must be at least 2 characters long");
            throw new InvalidSongNameOrArtistNameException("Filter must be at least 2 characters long.");
        }

        SongsPaginated songsPaginated = songRepositoryPort.findByNameOrArtistName(name, pageNumber);

        checkSongsPaginatedValid(songsPaginated);

        List<SongDTO> songDTOS = convertSongListToDTOList(songsPaginated.getData());
        return new SongResponseDTO(songDTOS, songsPaginated.getTotalElements());
    }

    @Override
    public SongResponseDTO findAllSongs(int pageNumber)
            throws SongsNotFoundException {
        SongsPaginated songsPaginated = songRepositoryPort.findAllSongs(pageNumber);

        checkSongsPaginatedValid(songsPaginated);

        List<SongDTO> songDTOS = convertSongListToDTOList(songsPaginated.getData());
        return new SongResponseDTO(songDTOS, songsPaginated.getTotalElements());
    }

    private List<SongDTO> convertSongListToDTOList(List<Song> songs){
        return songs.stream()
                .map(Song::toDTO)
                .collect(Collectors.toList());
    }

    private void checkSongsPaginatedValid(SongsPaginated songsPaginated) throws SongsNotFoundException {
        if(songsPaginated == null){
            logger.error("Recebe o erro No songs were found");
            throw new SongsNotFoundException("No songs were found.");
        }
    }
}
