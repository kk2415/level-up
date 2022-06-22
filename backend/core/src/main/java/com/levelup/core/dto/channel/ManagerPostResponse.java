package com.levelup.core.dto.channel;

import com.levelup.core.domain.Article.ChannelPost;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ManagerPostResponse{
    private Long id;
    private String title;
    private String writer;

    public ManagerPostResponse(ChannelPost channelPost) {
        this.id = channelPost.getArticleId();
        this.title = channelPost.getTitle();
        this.writer = channelPost.getWriter();
    }
}
