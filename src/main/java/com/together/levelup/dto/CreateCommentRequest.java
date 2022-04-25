package com.together.levelup.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateCommentRequest {

    @NotNull
    private Long memberId;

    @NotNull
    private Long channelId;

    @NotNull
    private String content;

}
