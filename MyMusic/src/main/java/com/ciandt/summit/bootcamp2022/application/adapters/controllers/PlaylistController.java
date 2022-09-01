package com.ciandt.summit.bootcamp2022.application.adapters.controllers;

import com.ciandt.summit.bootcamp2022.application.adapters.controllers.docs.PlaylistControllerDocs;
import com.ciandt.summit.bootcamp2022.domains.exceptions.playlists.PlaylistsNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.DuplicatedSongInPlaylist;
import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.SongsNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.playlists.dtos.PlaylistSongsRequestDTO;
import com.ciandt.summit.bootcamp2022.domains.playlists.ports.interfaces.PlaylistServicePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/playlists")
public class PlaylistController implements PlaylistControllerDocs {

    private static Logger logger = LoggerFactory.getLogger(PlaylistController.class.getSimpleName());
    @Autowired
    private PlaylistServicePort playlistServicePort;

    @PostMapping("/{playlistId}/musicas")
    public ResponseEntity<?> addSongsToPlaylist(@PathVariable String playlistId,
                                                @RequestBody PlaylistSongsRequestDTO playlistSongsRequestDTO)
            throws SongsNotFoundException, PlaylistsNotFoundException, DuplicatedSongInPlaylist {
            logger.info("Recebendo Request Post para "+ playlistId+"/musicas");
        playlistServicePort.addSongsToPlaylist(playlistId, playlistSongsRequestDTO.getData());

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
