package com.levelup.core.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class CreateExceptionResponse {

    private LocalDateTime timeStamp;
    Map<String, String> message = new HashMap<>();
    private String exception;
    private String path;

}
