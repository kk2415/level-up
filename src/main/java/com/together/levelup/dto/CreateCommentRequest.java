package com.together.levelup.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateCommentRequest {

    @NotNull
    private String memberEmail;

    @NotNull
    private Long postId;

    @NotNull
    private String content;

}
