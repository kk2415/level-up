package com.levelup.core.dto.channelNotice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChannelNoticeResponse {

    private Long id;
    private Long memberId;
    private String title;
    private String writer;
    private String content;
    private Long views;
    private Long voteCount;
    private String dateCreated;
    private int commentCount;

}
