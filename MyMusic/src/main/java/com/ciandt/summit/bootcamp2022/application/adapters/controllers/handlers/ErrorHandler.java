package com.ciandt.summit.bootcamp2022.application.adapters.controllers.handlers;

import com.ciandt.summit.bootcamp2022.domains.exceptions.playlists.PlaylistSongLimitExceededException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.playlists.PlaylistsNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.DuplicatedSongInPlaylist;
import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.InvalidSongNameOrArtistNameException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.songs.SongsNotFoundException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.tokens.BadAuthRequestException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.tokens.UnauthorizedException;
import com.ciandt.summit.bootcamp2022.domains.exceptions.users.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RestController
public class ErrorHandler extends ResponseEntityExceptionHandler {

    private ResponseEntity<ExceptionResponse> buildResponseEntityException(String description, String details, HttpStatus httpStatus) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(LocalDateTime.now(), description);

        if (details != null) {
            exceptionResponse.setDetails(details);
        }

        logger.error(description);
        return new ResponseEntity<>(exceptionResponse, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception exception, WebRequest request) {
        return this.buildResponseEntityException(
                exception.getMessage(),
                null,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(SongsNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleSongNotFoundExceptions(Exception exception, WebRequest request) {
        String exceptionMessage = exception.getMessage();
        return buildResponseEntityException(exceptionMessage, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidSongNameOrArtistNameException.class)
    public final ResponseEntity<ExceptionResponse> handleInvalidSongNameExceptions(Exception exception, WebRequest request) {
        String exceptionMessage = exception.getMessage();
        return buildResponseEntityException(exceptionMessage, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadAuthRequestException.class)
    public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(Exception exception, WebRequest request) {
        String exceptionMessage = exception.getMessage();
        return buildResponseEntityException(exceptionMessage, "verify the headers before send request", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<ExceptionResponse> handleUnauthorizedExceptions(Exception exception, WebRequest request) {
        String exceptionMessage = exception.getMessage();
        return buildResponseEntityException(exceptionMessage, null, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(PlaylistsNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handlePlaylistsNotFoundException(Exception exception, WebRequest request) {
        String exceptionMessage = exception.getMessage();
        return buildResponseEntityException(exceptionMessage, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicatedSongInPlaylist.class)
    public final ResponseEntity<ExceptionResponse> handleDuplicatedSongInPlaylist(Exception exception, WebRequest request) {
        String exceptionMessage = exception.getMessage();
        return buildResponseEntityException(exceptionMessage, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleUserNotFoundException(Exception exception, WebRequest request) {
        String exceptionMessage = exception.getMessage();
        return buildResponseEntityException(exceptionMessage, null, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PlaylistSongLimitExceededException.class)
    public final ResponseEntity<ExceptionResponse> handlePlaylistSongLimitExceededException(Exception exception, WebRequest request) {
        String exceptionMessage = exception.getMessage();
        return buildResponseEntityException(exceptionMessage, null, HttpStatus.BAD_REQUEST);
    }
}
