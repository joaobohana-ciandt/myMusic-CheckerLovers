package com.ciandt.summit.bootcamp2022.application.adapters.controllers.handlers;

import java.io.Serializable;
import java.time.LocalDateTime;


public class ExceptionResponse implements Serializable {

    private static final long serialVerersionUID =1L;

    private LocalDateTime timestamp;
    private String message;
    private String details;

    public ExceptionResponse(LocalDateTime localDateTime, String message) {
        this.timestamp = localDateTime;
        this.message = message;
    }

    public ExceptionResponse(LocalDateTime localDateTime, String message, String details) {
        this(localDateTime, message);

        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
