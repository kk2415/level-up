package com.levelup.core.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class CommentResponse {

    private Long id;
    private String writer;
    private String content;
    private String dateCreated;
    private Long voteCount;
    private Long replyCount;

}
