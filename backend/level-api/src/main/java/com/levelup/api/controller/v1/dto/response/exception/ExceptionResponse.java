package com.levelup.api.controller.v1.dto.response.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.levelup.common.exception.ErrorCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ExceptionResponse {

    private int status;
    private String message;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timeStamp;

    protected ExceptionResponse() {}

    private ExceptionResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timeStamp = LocalDateTime.now();
    }

    protected ExceptionResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.message = errorCode.getMessage();
        this.timeStamp = LocalDateTime.now();
    }

    public static ExceptionResponse from(ErrorCode errorCode) {
        return new ExceptionResponse(errorCode.getStatus(), errorCode.getMessage());
    }

    public static ExceptionResponse of(int status, String message) {
        return new ExceptionResponse(status, message);
    }
}
