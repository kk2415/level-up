package com.levelup.api.controller.v1.dto.request.channel;

import com.levelup.channel.domain.service.dto.ChannelArticleDto;
import com.levelup.channel.domain.constant.ChannelArticleCategory;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ChannelArticleRequest {

    @NotNull
    private String title;

    @NotNull
    private String content;

    @NotNull
    private ChannelArticleCategory category;

    public static ChannelArticleRequest of(
            String title,
            String content,
            ChannelArticleCategory category
    ) {
        return new ChannelArticleRequest(
                title,
                content,
                category
        );
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
                .category(category)
                .build();
    }
}
