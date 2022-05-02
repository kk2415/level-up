package com.together.levelup.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChannelNoticeRequest {

    @NotNull
    private String title;

    @NotNull
    private String writer;

    @NotNull
    private String content;

}
