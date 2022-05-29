package com.levelup.core.dto.notice_channel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PagingChannelNoticeResponse {

    private Long id;
    private String title;
    private String writer;
    private String content;
    private Long views;
    private String dateCreated;
    private int commentCount;
    private int allNoticeCount;

}
