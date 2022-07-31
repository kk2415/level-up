package com.levelup.core.dto.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ExceptionResponse {

    private LocalDateTime timeStamp;
    private String message;
    private String exception;
    private String path;

    private ExceptionResponse(LocalDateTime timeStamp, String message, String exception, String path) {
        this.timeStamp = timeStamp;
        this.message = message;
        this.exception = exception;
        this.path = path;
    }

    public static ExceptionResponse of(LocalDateTime timeStamp, String message, String exception, String path) {
        return new ExceptionResponse(timeStamp, message, exception, path);
    }
}
