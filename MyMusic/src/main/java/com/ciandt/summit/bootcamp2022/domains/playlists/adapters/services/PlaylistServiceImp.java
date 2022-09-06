package com.ciandt.summit.bootcamp2022.domains.playlists.adapters.services;

import com.ciandt.summit.bootcamp2022.application.adapters.controllers.PlaylistController;
import com.ciandt.summit.bootcamp2022.domains.exceptions.playlists.PlaylistsNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.DuplicatedSongInPlaylist;
import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.SongsNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.users.UserNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.playlists.Playlist;
import com.ciandt.summit.bootcamp2022.domains.playlists.ports.interfaces.PlaylistServicePort;
import com.ciandt.summit.bootcamp2022.domains.playlists.ports.repositories.PlaylistRespositoryPort;
import com.ciandt.summit.bootcamp2022.domains.songs.Song;
import com.ciandt.summit.bootcamp2022.domains.songs.dtos.SongDTO;
import com.ciandt.summit.bootcamp2022.domains.songs.ports.repositories.SongRepositoryPort;
import com.ciandt.summit.bootcamp2022.infra.adapters.entities.PlaylistEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PlaylistServiceImp implements PlaylistServicePort {

    private static Logger logger = LoggerFactory.getLogger(PlaylistController.class.getSimpleName());
    private final PlaylistRespositoryPort playlistRespositoryPort;
    private final SongRepositoryPort songRepositoryPort;

    public PlaylistServiceImp(PlaylistRespositoryPort playlistRespositoryPort, SongRepositoryPort songRepositoryPort) {
        this.playlistRespositoryPort = playlistRespositoryPort;
        this.songRepositoryPort = songRepositoryPort;
    }

    @Override
    public Playlist addSongsToPlaylist(String id, String userId, List<SongDTO> songs) throws SongsNotFoundException, PlaylistsNotFoundException, DuplicatedSongInPlaylist, UserNotFoundException {
        Playlist playlist = this.playlistRespositoryPort.findById(id);

        for (SongDTO songDTO : songs) {
            Song song = this.songRepositoryPort.findById(songDTO.getId());

            if (playlist.getSongs().contains(song)) {
                logger.error("Recebe o erro Cannot add duplicate song(s) to playlist");
                throw new DuplicatedSongInPlaylist("Cannot add duplicate song(s) to playlist");
            }

            playlist.getSongs().add(song);
        }

        return this.playlistRespositoryPort.addSong(new PlaylistEntity(playlist));
    }

    @Override
    public Playlist removeSongFromPlaylist(String id, String songId) throws SongsNotFoundException, PlaylistsNotFoundException {
        Playlist playlist = this.playlistRespositoryPort.findById(id);
        Song songToRemove = this.songRepositoryPort.findById(songId);

        if (!playlist.getSongs().contains(songToRemove)) {
            logger.error("Recebe o erro Specified song was not found in playlist");
            throw new SongsNotFoundException("Specified song was not found in playlist");
        }

        playlist.getSongs().remove(songToRemove);

        return this.playlistRespositoryPort.addSong(new PlaylistEntity(playlist));
    }
}
