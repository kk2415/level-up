package com.together.levelup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class PostResponse {

    private String title;
    private String writer;
    private String content;
    private String dateCreated;
    private Long voteCount;
    private int commentCount;

}
