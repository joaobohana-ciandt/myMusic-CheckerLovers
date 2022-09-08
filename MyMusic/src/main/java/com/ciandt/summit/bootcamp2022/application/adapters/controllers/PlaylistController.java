package com.ciandt.summit.bootcamp2022.application.adapters.controllers;

import com.ciandt.summit.bootcamp2022.application.adapters.controllers.docs.PlaylistControllerDocs;
import com.ciandt.summit.bootcamp2022.domains.exceptions.playlists.PlaylistSongLimitExceededException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.playlists.PlaylistsNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.DuplicatedSongInPlaylist;
import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.SongsNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.users.UserNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.playlists.dtos.PlaylistSongsRequestDTO;
import com.ciandt.summit.bootcamp2022.domains.playlists.ports.interfaces.PlaylistServicePort;
import com.ciandt.summit.bootcamp2022.domains.users.ports.interfaces.UserServicePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/playlists")
public class PlaylistController implements PlaylistControllerDocs {

    private static final Logger logger = LoggerFactory.getLogger(PlaylistController.class.getSimpleName());
    @Autowired
    private PlaylistServicePort playlistServicePort;

    @PostMapping("/{playlistId}/{userId}/musicas")
    public ResponseEntity<?> addSongsToPlaylist(@PathVariable String playlistId,
                                                @PathVariable String userId,
                                                @RequestBody PlaylistSongsRequestDTO playlistSongsRequestDTO)
            throws SongsNotFoundException, PlaylistsNotFoundException, DuplicatedSongInPlaylist, UserNotFoundException, PlaylistSongLimitExceededException {
            logger.info("Recebendo Request Post para "+ playlistId+ "/musicas");
        playlistServicePort.addSongsToPlaylist(playlistId, userId, playlistSongsRequestDTO.getData());

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{playlistId}/musicas/{musicaId}")
    public ResponseEntity<?> removeSongFromPlaylist(@PathVariable String playlistId,
                                                    @PathVariable(name = "musicaId") String songId)
            throws SongsNotFoundException, PlaylistsNotFoundException {
        logger.info("Recebendo Request Delete para "+ playlistId + "/musicas" + songId);
        playlistServicePort.removeSongFromPlaylist(playlistId,songId);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
