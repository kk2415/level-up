package com.together.levelup.dto.notice;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class NoticeResponse {

    private Long id;
    private String title;
    private String writer;
    private String content;
    private String dateCreated;
    private Long views;
    private int commentCount;

}
