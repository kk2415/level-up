package com.levelup.api.dto.channelPost;

import com.levelup.core.domain.Article.ArticleType;
import com.levelup.core.domain.channelPost.ChannelPost;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.channelPost.PostCategory;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class ChannelPostRequest {

    private String title;
    private String content;
    private ArticleType articleType;
    private PostCategory postCategory;

    private ChannelPostRequest(String title, String content, ArticleType articleType, PostCategory postCategory) {
        this.title = title;
        this.content = content;
        this.articleType = articleType;
        this.postCategory = postCategory;
    }

    public static ChannelPostRequest of(String title, String content, ArticleType articleType, PostCategory postCategory) {
        return new ChannelPostRequest(title, content, articleType, postCategory);
    }

    public ChannelPost toEntity(Member member, Channel channel) {
        ChannelPost channelPost = ChannelPost.builder()
                .channelPostCategory(postCategory)
                .channel(channel)
                .build();

        channelPost.setMember(member);
        channelPost.setTitle(title);
        channelPost.setContent(content);
        channelPost.setViews(0L);
        channelPost.setArticleType(articleType);
        return channelPost;
    }
}
