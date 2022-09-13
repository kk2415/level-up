package com.levelup.api.dto.request.channelPost;

import com.levelup.api.dto.service.channelPost.ChannelPostDto;
import com.levelup.core.domain.article.ArticleType;
import com.levelup.core.domain.channelPost.ChannelPost;
import com.levelup.core.domain.channel.Channel;
import com.levelup.core.domain.member.Member;
import com.levelup.core.domain.channelPost.PostCategory;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ChannelPostRequest {

    private String title;
    private String content;
    private ArticleType articleType;
    private PostCategory postCategory;

    protected ChannelPostRequest() {}

    private ChannelPostRequest(String title, String content, ArticleType articleType, PostCategory postCategory) {
        this.title = title;
        this.content = content;
        this.articleType = articleType;
        this.postCategory = postCategory;
    }

    public static ChannelPostRequest of(String title, String content, ArticleType articleType, PostCategory postCategory) {
        return new ChannelPostRequest(title, content, articleType, postCategory);
    }

    public ChannelPostDto toDto() {
        return ChannelPostDto.builder()
                .channelPostId(null)
                .memberId(null)
                .title(title)
                .writer(null)
                .content(content)
                .createdAt(null)
                .voteCount(0L)
                .views(0L)
                .commentCount(0L)
                .postCategory(postCategory)
                .articleType(articleType)
                .build();
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
