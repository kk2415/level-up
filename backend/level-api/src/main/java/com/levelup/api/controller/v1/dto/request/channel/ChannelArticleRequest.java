package com.levelup.api.controller.v1.dto.request.channel;

import com.levelup.channel.domain.service.dto.ChannelArticleDto;
import com.levelup.channel.domain.entity.ChannelArticleCategory;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Builder
@Getter
public class ChannelArticleRequest {

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private ChannelArticleCategory category;

    protected ChannelArticleRequest() {}

    private ChannelArticleRequest(String title, String content, ChannelArticleCategory category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public static ChannelArticleRequest of(String title, String content, ChannelArticleCategory category) {
        return new ChannelArticleRequest(title, content, category);
    }

    public ChannelArticleDto toDto() {
        return ChannelArticleDto.builder()
                .channelPostId(null)
                .memberId(null)
                .title(title)
                .writer(null)
                .content(content)
                .createdAt(null)
                .voteCount(0L)
                .views(0L)
                .commentCount(0L)
                .postCategory(category)
                .build();
    }
}
