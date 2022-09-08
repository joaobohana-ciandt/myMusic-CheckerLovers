package com.ciandt.summit.bootcamp2022.application.adapters.controllers;

import com.ciandt.summit.bootcamp2022.application.adapters.controllers.docs.SongControllerDocs;
import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.InvalidSongNameOrArtistNameException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.SongsNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.songs.dtos.SongResponseDTO;

import com.ciandt.summit.bootcamp2022.domains.songs.ports.interfaces.SongServicePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SongsController implements SongControllerDocs {

    private static final Logger logger = LoggerFactory.getLogger(SongsController.class.getSimpleName());

    @Autowired
    private SongServicePort songServicePort;

    @GetMapping("/musicas")
    @ResponseBody
    public ResponseEntity<SongResponseDTO> findSongsByNameOrArtistName(@RequestParam(name = "filtro", required = false) String filter,
                                                                       @RequestParam(name = "pagina", defaultValue = "0") int pageNumber)
            throws SongsNotFoundException, InvalidSongNameOrArtistNameException {

        logger.info("Recebendo Request Get para /musicas");
        SongResponseDTO response;
        if(filter == null){
            response = songServicePort.findAllSongs(pageNumber);
        } else {
            response = songServicePort.findByNameOrArtistName(filter, pageNumber);
        }

        if(response.getData().isEmpty()){
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok().body(response);
    }
}
