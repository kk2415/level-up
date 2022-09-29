package com.levelup.api.controller.v1.dto.request.file;

import lombok.Data;
import lombok.Getter;

@Getter
public class Base64Dto {

    private String name;
    private String base64;

    protected Base64Dto() {}
}
