package com.levelup.core.dto.channel;

import com.levelup.core.domain.Article.ArticleType;
import lombok.Data;

@Data
public class ChannelPostRequest {

    private Long channelId;
    private String title;
    private String writer;
    private String content;
    private ArticleType category;

}
