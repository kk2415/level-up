package com.levelup.core.dto.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ExceptionResponse {

    @JsonSerialize(using = LocalDateTimeSerializer.class)
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
