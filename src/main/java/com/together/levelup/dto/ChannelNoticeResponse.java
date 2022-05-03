package com.together.levelup.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChannelNoticeResponse {

    private Long id;
    private String title;
    private String writer;
    private String content;
    private Long views;
    private String dateCreated;
    private int commentCount;

}
