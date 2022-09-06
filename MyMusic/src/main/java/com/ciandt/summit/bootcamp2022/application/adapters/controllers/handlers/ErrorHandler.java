package com.ciandt.summit.bootcamp2022.application.adapters.controllers.handlers;

import com.ciandt.summit.bootcamp2022.domains.exceptions.playlists.PlaylistsNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.DuplicatedSongInPlaylist;
import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.InvalidSongNameOrArtistNameException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.SongsNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.tokens.BadAuthRequestException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.tokens.UnauthorizedException;
import com.ciandt.summit.bootcamp2022.domains.playlists.Playlist;
import com.ciandt.summit.bootcamp2022.domains.songs.dtos.SongDTO;
import com.ciandt.summit.bootcamp2022.domains.songs.dtos.SongResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@ControllerAdvice
@RestController
public class ErrorHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<ExceptionResponse> buildResponseEntityExeption(String description, String details, HttpStatus httpStatus) {
        ExceptionResponse exceptionResponse;
        LocalDateTime localDateTime = LocalDateTime.now();

        if (details != null) {
            exceptionResponse = new ExceptionResponse(localDateTime, description, details);
        } else {
            exceptionResponse = new ExceptionResponse(localDateTime, description);
        }

        logger.error(description);
        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception exception, WebRequest request) {
        return this.buildResponseEntityExeption(
                exception.getMessage(),
                null,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(SongsNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleSongNotFoundExceptions(Exception exception, WebRequest request) {
        String exceptionMessage = exception.getMessage();
        return buildResponseEntityExeption(exceptionMessage, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidSongNameOrArtistNameException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidSongNameExceptions(Exception exception, WebRequest request) {
        String exceptionMessage = exception.getMessage();
        return buildResponseEntityExeption(exceptionMessage, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadAuthRequestException.class)
    public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(Exception exception, WebRequest request) {
        String exceptionMessage = exception.getMessage();
        return buildResponseEntityExeption(exceptionMessage, "verify the headers before send request", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<ExceptionResponse> handleUnauthorizedExceptions(Exception exception, WebRequest request) {
        String exceptionMessage = exception.getMessage();
        return buildResponseEntityExeption(exceptionMessage, null, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PlaylistsNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handlePlaylistsNotFoundException(Exception exception, WebRequest request) {
        String exceptionMessage = exception.getMessage();
        return buildResponseEntityExeption(exceptionMessage, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicatedSongInPlaylist.class)
    public final ResponseEntity<ExceptionResponse> handleDuplicatedSongInPlaylist(Exception exception, WebRequest request) {
        String exceptionMessage = exception.getMessage();
        return buildResponseEntityExeption(exceptionMessage, null, HttpStatus.BAD_REQUEST);
    }
}
