package com.levelup.common.web.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OkResponse<T> {

    private int status;
    private T result;

    protected OkResponse() {}

    public OkResponse(int status, T result) {
        this.status = status;
        this.result = result;
    }

    public OkResponse(HttpStatus status, T result) {
        this.status = status.value();
        this.result = result;
    }
}
