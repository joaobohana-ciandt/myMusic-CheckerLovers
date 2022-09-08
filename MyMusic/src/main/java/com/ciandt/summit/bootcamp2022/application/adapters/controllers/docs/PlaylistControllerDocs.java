package com.ciandt.summit.bootcamp2022.application.adapters.controllers.docs;

import com.ciandt.summit.bootcamp2022.domains.exceptions.playlists.PlaylistSongLimitExceededException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.playlists.PlaylistsNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.DuplicatedSongInPlaylist;
import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.SongsNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.users.UserNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.playlists.dtos.PlaylistSongsRequestDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;


public interface PlaylistControllerDocs {

    @ApiOperation("Add songs to existing playlist")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Added songs successfully"),
            @ApiResponse(code = 400, message = "Could not find specified playlist or song(s) in the database"),
            @ApiResponse(code = 401, message = "")
    })
    ResponseEntity<?> addSongsToPlaylist(@PathVariable String playlistId,
                                         @PathVariable String userId,
                                                @RequestBody PlaylistSongsRequestDTO playlistSongsRequestDTO)
            throws SongsNotFoundException, PlaylistsNotFoundException, DuplicatedSongInPlaylist, UserNotFoundException, PlaylistSongLimitExceededException;

    @ApiOperation("Remove song from existing playlist")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Removed song successfully"),
            @ApiResponse(code = 400, message = "Could not find specified playlist or song(s) in the database"),
            @ApiResponse(code = 401, message = "")
    })
    ResponseEntity<?> removeSongFromPlaylist(@PathVariable String playlistId,
                                             @PathVariable String songId)
            throws SongsNotFoundException, PlaylistsNotFoundException;

}
