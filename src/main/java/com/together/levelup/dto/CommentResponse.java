package com.together.levelup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommentResponse {

    private String writer;
    private String content;
    private String dateCreated;
    private Long voteCount;

}
